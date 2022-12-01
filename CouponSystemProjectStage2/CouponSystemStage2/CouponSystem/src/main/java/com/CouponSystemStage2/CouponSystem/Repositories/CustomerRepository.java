/**
 * The CustomerRepository holds the functions that will be used by the Services. These functions form a connection to the dataBase
 * by JPA and Hibernate.
 * <p>
 * ABOUT THE FUNCTIONS AND THEIR USES:
 * existsByEmailAndPassword - this function checks if the customer actually exists in the database.(Using email and password)
 * existsByEmail - this function checks if the customer actually exists in the database.(Using email)
 * getByEmailAndPassword - The login uses this function. This function returns the customer by using email and password.
 * updateCustomer - This function updates the new details that you changed to the current customer object in the DataBase.
 * wasCouponPurchased - This function checks if this coupon exists in the log list by couponId and customerId.
 * deleteCustomerFromPurchaseList - This function deletes the log from coupons_vs_customers table by customerId.
 */
package com.CouponSystemStage2.CouponSystem.Repositories;

import com.CouponSystemStage2.CouponSystem.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update customers as c set c.first_name= :#{#customer.firstName} ,  c.last_name= :#{#customer.lastName} , c.password = :#{#customer.password}, c.email = :#{#customer.email}  where c.id= :#{#customer.id}", nativeQuery = true)
    void updateCustomer(@Param("customer") Customer customer);

    Customer getByEmailAndPassword(String email, String password);

    @Query(value = " SELECT IF(COUNT(*) > 0, 'true', 'false') from coupons_vs_customers as cc  where   cc.coupon_id= :couponId AND cc.customer_id = :customerId", nativeQuery = true)
    boolean wasCouponPurchased(@Param("couponId") int couponId, @Param("customerId") int customerId);

    @Transactional
    @Modifying
    @Query(value = "delete from coupons_vs_customers where customer_id= :customerId", nativeQuery = true)
    void deleteCustomerFromPurchaseList(@Param("customerId") int customerId);

}

