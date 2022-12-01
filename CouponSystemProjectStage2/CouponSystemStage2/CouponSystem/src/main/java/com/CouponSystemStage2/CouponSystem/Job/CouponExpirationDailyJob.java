/**
 * CouponExpirationDailyJob is a thread with a daily task of erasing all the coupons that have already expired.
 * The task is done every 24hrs. (with the use of the sleep function)
 * It doesn't stop till the end of program where the program closes it with the flag "quit" where it interrupts the sleep.
 * This thread runs parallel to the whole program.
 */
package com.CouponSystemStage2.CouponSystem.Job;

import com.CouponSystemStage2.CouponSystem.DesignColors.TextColors;
import com.CouponSystemStage2.CouponSystem.Entities.Coupon;
import com.CouponSystemStage2.CouponSystem.Repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CouponExpirationDailyJob implements Runnable {
    final static long DAY = 1 * 1000 * 60 * 60 * 24;

    @Autowired
    CouponRepository couponRepository;

    private boolean quit = false;

    public CouponExpirationDailyJob() {
    }

    @Override
    public void run() {
        while (!quit) {
            for (Coupon coupon : couponRepository.findAllByEndDateLessThan((new Date(System.currentTimeMillis())))
            ) {
                if (coupon != null) {
                    couponRepository.delete(coupon);
                }
            }
            try {
                Thread.sleep(DAY);
            } catch (InterruptedException e) {
                System.out.println(TextColors.ANSI_PURPLE + "We are closing the program\nThe JOB has stopped its activity!" + TextColors.ANSI_RESET);
            }
        }
    }
    public void stop() {
        quit = true;
    }
}