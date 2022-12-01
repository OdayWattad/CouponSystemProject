/**
 * This program was created by Wattad Ahmad and Wattad Oday.
 * This class contains three functions:
 * Clear() - the purpose of this function to delete all data
 * TestAll() - tests all the program functions and asserts the availability to use this program safely.
 */
package com.CouponSystemStage2.CouponSystem;

import com.CouponSystemStage2.CouponSystem.ClearDataBase_Repositories.ClearDataBase;
import com.CouponSystemStage2.CouponSystem.Data.InitialData;
import com.CouponSystemStage2.CouponSystem.Services.ServiceException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CouponSystemApplication {
    public static void main(String[] args) throws ServiceException {
        ConfigurableApplicationContext ctx = SpringApplication.run(CouponSystemApplication.class, args);
        ClearDataBase clearDataBase = ctx.getBean(ClearDataBase.class);
        clearDataBase.Clear();
        InitialData initialData = ctx.getBean(InitialData.class);
        initialData.fillData();
    }
}