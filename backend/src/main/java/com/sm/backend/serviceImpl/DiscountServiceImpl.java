package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.DiscountAlreadyExistException;
import com.sm.backend.exceptionalHandling.ProductAlreadyExistsException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Discount;
import com.sm.backend.repository.DiscountRepository;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.DiscountRequest;
import com.sm.backend.response.DiscountResponse;
import com.sm.backend.service.DiscountService;
import com.sm.backend.util.DiscountType;
import com.sm.backend.util.WaiverMode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductVariantRepository variantRepository;
    public DiscountServiceImpl(DiscountRepository discountRepository, ProductVariantRepository variantRepository) {
        this.discountRepository = discountRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    public void createDiscount(DiscountRequest request) {
        Optional<Discount> discountCheck = discountRepository.findDiscountByVariantId(request.getVariantId());
        if(discountCheck.isPresent()){
            throw new DiscountAlreadyExistException("discount already exist");
        }
        else {
        Discount discount = new Discount();
        discount.setDiscountName(request.getDiscountName());
//        discount.setVariant(variantRepository);
        if(request.getWaiverMode()==WaiverMode.FIXED){
            discount.setWaiverMode(WaiverMode.FIXED);

        }
        if(request.getWaiverMode()== WaiverMode.PERCENT){
            discount.setWaiverMode(WaiverMode.PERCENT);
        }
        discount.setVariant(variantRepository.findById(request.getVariantId())
                .orElseThrow(()->new ResourceNotFoundException("invalid id")));
        discount.setDiscountValue(request.getDiscountValue());
        discount.setEndDateTime(request.getEndDateTime());
       discount.setIsActive(true);
        discountRepository.save(discount);
    }
    }

    @Override
    public List<DiscountResponse> getAll() {
        List<Discount> discounts = discountRepository.findAll();
     return discounts.stream().map(DiscountResponse::new).toList();
    }

    @Override
    public DiscountResponse getById(Long id) {
Discount discount = discountRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("invalid id"));
return new DiscountResponse(discount);
    }

//    @Scheduled(cron = "0 0 12 * * ?")//everyday 12 pm
    @Scheduled(cron = "0 * * * * ?")//every minute.
    public void changeActiveStatusBasedOnStartEndDate(){
        List<Discount>discounts=discountRepository.findAll();
        discounts.stream().map((x)->{
//            if (LocalDateTime.now().isBefore(x.getEndDateTime())&&LocalDateTime.now().isAfter(x.getStartDateTime())){
//                x.setIsActive(true);
//                discountRepository.save(x);
//
//            }
            if(LocalDateTime.now().isAfter(x.getEndDateTime())){
//                x.setIsActive(false);
////                discountRepository.save(x);
                discountRepository.delete(x);
            }
//            discountRepository.save(x);
             return x;
        }).toList();
//    discountRepository.saveAll(discounts);
        System.out.println("Scheduler is running");
    }

    @Override
    public void delete(Long id) {
        discountRepository.delete(discountRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("invalid id")));
    }

    @Override
    public void update(DiscountRequest request, Long id) {
        Discount discount = discountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
       if(request.getDiscountName()!=null){
           discount.setDiscountName(request.getDiscountName());
       }
       if (request.getDiscountValue()!=null){
           discount.setDiscountValue(request.getDiscountValue());
       }
       if(request.getWaiverMode()!=null){
           discount.setWaiverMode(request.getWaiverMode());
       }
       if(request.getEndDateTime()!=null){
           discount.setEndDateTime(request.getEndDateTime());
       }
       if(request.getVariantId()!=null){
           discount.setVariant(variantRepository.findById(request.getVariantId())
                   .orElseThrow(()->new ResourceNotFoundException("invalid id")));
       }

       discountRepository.save(discount);
    }
}
