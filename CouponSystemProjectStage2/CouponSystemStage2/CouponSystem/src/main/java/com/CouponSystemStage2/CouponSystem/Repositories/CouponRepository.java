/**
 * The CouponRepository holds the functions that will be used by the Services. These functions form a connection to the dataBase
 * by JPA and Hibernate.
 * <p>
 * ABOUT THE FUNCTIONS AND THEIR USES:
 * existsByCompanyAndTitle - This function checks if the coupon actually exists in the database by Company and Title.
 * updateCoupon -  This function updates the new details that you changed to the current coupon object in the DataBase.
 * allCustomerCoupons - This function returns all the coupons for one customer from the dataBase.
 * allCustomerCouponsByCategory - This function returns all the coupons for one customer from the database filtered by certain category.
 * allCustomerCouponsBelowMaxPrice - This function returns all the coupons for one customer from the database filtered by prices less than certain price.
 * findAllByEndDateLessThan - This function gets all the coupons from the database filtered by EndDate less than certain date(CurrentDate).
 * findAllByCompany - This function gets all coupons from dataBase for one company.
 * findAllByCompanyAndCategory - This function gets all the coupons from dataBase for one company filtered by category.
 * findAllByCompanyAndPriceLessThan - This function gets all the coupons from the dataBase for one company filtered by prices less than certain price.
 */
package com.CouponSystemStage2.CouponSystem.Repositories;

import com.CouponSystemStage2.CouponSystem.Entities.Category;
import com.CouponSystemStage2.CouponSystem.Entities.Company;
import com.CouponSystemStage2.CouponSystem.Entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    boolean existsByCompanyAndTitle(Company company, String title);

    @Transactional
    @Modifying
    @Query(value = "update coupons as c set c.title= :#{#coupon.title} , c.start_date= :#{#coupon.startDate} , c.end_date = :#{#coupon.endDate} , c.category_id= :#{#coupon.category.ordinal} , c.image= :#{#coupon.image} ,  c.price= :#{#coupon.price} , c.description = :#{#coupon.description} , c.amount = :#{#coupon.amount}  where c.id= :#{#coupon.id}", nativeQuery = true)
    void updateCoupon(@Param("coupon") Coupon coupon);

    @Query(value = " select * from coupons_vs_customers as t1 join coupons as t2  ON t1.coupon_id=t2.id  where customer_id= :customerId ", nativeQuery = true)
    ArrayList<Coupon> allCustomerCoupons(@Param("customerId") int customerId);

    @Query(value = "select * from coupons_vs_customers t1 join coupons t2 ON t1.COUPON_ID= t2.ID where customer_id=:customerId AND category_id= :categoryId ", nativeQuery = true)
    ArrayList<Coupon> allCustomerCouponsByCategory(@Param("customerId") int customerId, @Param("categoryId") int categoryId);

    @Query(value = "select * from coupons_vs_customers t1 join coupons t2 ON t1.COUPON_ID= t2.ID where customer_id=:customerId AND price<:maxPrice ", nativeQuery = true)
    ArrayList<Coupon> allCustomerCouponsBelowMaxPrice(@Param("customerId") int customerId, @Param("maxPrice") double maxPrice);

    ArrayList<Coupon> findAllByEndDateLessThan(Date date);

    ArrayList<Coupon> findAllByCompany(Company company);

    ArrayList<Coupon> findAllByCompanyAndCategory(Company company, Category category);

    ArrayList<Coupon> findAllByCompanyAndPriceLessThan(Company company, double maxPrice);

}
