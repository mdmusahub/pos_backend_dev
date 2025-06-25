package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Discount;
import com.sm.backend.repository.DiscountRepository;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.request.DiscountRequest;
import com.sm.backend.response.DiscountResponse;
import com.sm.backend.service.DiscountService;
import com.sm.backend.util.DiscountType;
import com.sm.backend.util.WaiverMode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository, ProductRepository productRepository) {
        this.discountRepository = discountRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createDiscount(DiscountRequest request) {
        Discount discount = new Discount();
        discount.setDiscountName(request.getDiscountName());
        if (request.getDiscountType() == DiscountType.ORDER_LEVEL) {
            discount.setDiscountType(DiscountType.ORDER_LEVEL);
        }
        if (request.getDiscountType() == DiscountType.PRODUCT_LEVEL) {
            discount.setDiscountType(DiscountType.PRODUCT_LEVEL);
        }
        if (request.getWaiverMode() == WaiverMode.FIXED) {
            discount.setWaiverMode(WaiverMode.FIXED);
        }
        if (request.getWaiverMode() == WaiverMode.PERCENT) {
            discount.setWaiverMode(WaiverMode.PERCENT);
        }
        discount.setProduct(productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("invalid id")));
        discount.setDiscountValue(request.getDiscountValue());
        discount.setStartDateTime(request.getStartDateTime());
        discount.setEndDateTime(request.getEndDateTime());
        discount.setIsActive(false);
        discount.setMinimumPrice(request.getMinimumPrice());
        discount.setMinimumQuantity(request.getMinimumQuantity());
        discountRepository.save(discount);
    }

    @Override
    public List<DiscountResponse> getAll() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts.stream().map(DiscountResponse::new).toList();
    }

    @Override
    public DiscountResponse getById(Long id) {
        Discount discount = discountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        return new DiscountResponse(discount);
    }

    @Override
    public void deleteDiscount(Long id) {
        discountRepository.delete(discountRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id")));

    }

    @Scheduled(cron = "0 0 12 * * ?")//every day 12 pm
//    @Scheduled(cron = "0 * * * * ?")//every minute.
    public void changeActiveStatusBasedOnStartEndDate() {
        List<Discount> discounts = discountRepository.findAll();

        discounts.stream().map((x) -> {
            if (LocalDateTime.now().isBefore(x.getEndDateTime()) && LocalDateTime.now().isAfter(x.getStartDateTime())) {
                x.setIsActive(true);
            }
            if (LocalDateTime.now().isAfter(x.getEndDateTime())) {
                x.setIsActive(false);
            }
            discountRepository.save(x);
            return x;
        }).toList();
//    discountRepository.saveAll(discounts);
        System.out.println("Scheduler is running");
    }

}
