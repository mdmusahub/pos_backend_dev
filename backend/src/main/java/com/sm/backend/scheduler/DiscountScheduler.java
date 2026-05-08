package com.sm.backend.scheduler;

import com.sm.backend.model.Discount;
import com.sm.backend.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscountScheduler {

    private final DiscountRepository discountRepository;

        @Scheduled(cron = "0 0 12 * * ?")//everyday 12 pm
//    @Scheduled(cron = "0 * * * * ?")//every minute.
    public void changeActiveStatusBasedOnStartEndDate() {
        List<Discount> discounts = discountRepository.findAll();
        discounts.stream().map((x) -> {
//            if (LocalDateTime.now().isBefore(x.getEndDateTime())&&LocalDateTime.now().isAfter(x.getStartDateTime())){
//                x.setIsActive(true);
//                discountRepository.save(x);
//
//            }
            if (LocalDateTime.now().isAfter(x.getEndDateTime())) {
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
}
