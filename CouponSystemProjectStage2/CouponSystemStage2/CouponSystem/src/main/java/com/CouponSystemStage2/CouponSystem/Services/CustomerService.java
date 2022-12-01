/**
 * CustomerService includes all the functions that the user(Customer) can do in this program. The Service uses the repositories in order to connect
 * to the DataBase.
 *
 * The CompanyService throws SQL and Interrupted exceptions - to be handled later in the testAll.
 *
 * The functions and their uses:
 *    1. Login: The user needs to log in before he can do any functions. Here the program checks if the details he/she entered are correct.
 *    2. getCustomerByEmailAndPassword: Not available to the company. The loginManager uses this function.
 *    3. purchaseCoupon: The customer is able to purchase a coupon of his choice. Yet, he can't purchase the same coupon more than once,
 * he can't purchase a coupon that the company run out of ,and he can't purchase it if it is already expired. if he does, The program
 * will throw a custom exception (ServiceException) attached with proper message.
 *    4. getCustomerCoupons: The customer is able to view all of his coupons, or he can view them sorted by Category of their choice ,
 * or he can view them sorted by maxPrice of their choice.
 *    5. getCustomerDetails: The customer is able to view all of his details. (couponList excluded).
 *    6. getAllCoupons: The customer is able to view all the coupons, so he can choose a coupon to purchase.
 *
 */
package com.CouponSystemStage2.CouponSystem.Services;

import com.CouponSystemStage2.CouponSystem.DesignColors.TextColors;
import com.CouponSystemStage2.CouponSystem.Entities.Category;
import com.CouponSystemStage2.CouponSystem.Entities.Coupon;
import com.CouponSystemStage2.CouponSystem.Entities.Customer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Scope("prototype")
public class CustomerService extends ClientService {
    private int customerId;
    private Customer customer;

    public CustomerService() {
    }

    public boolean login(String email, String password) {
        return customerRepository.existsByEmailAndPassword(email, password);
    }

    public String purchaseCoupon(Coupon coupon)  {

        try {
            if (!customerRepository.wasCouponPurchased(coupon.getId(), this.customerId)) {
                if (coupon.getAmount() > 0) {
                    if (coupon.getEndDate().after((Calendar.getInstance()).getTime())) {
                        Coupon realCoupon=couponRepository.findById(coupon.getId()).get();
                        realCoupon.setAmount(coupon.getAmount() - 1);
                        realCoupon.addCustomer(customer);
                        System.out.println(coupon);
                        couponRepository.save(realCoupon);
                        System.out.println(TextColors.ANSI_BLUE + "Coupon has been purchased successfully!" + TextColors.ANSI_RESET);
                        return "" + coupon.getTitle() +" has been purchased successfully!";

                    } else
                        throw new ServiceException(" : This coupon has expired!");
                } else
                    throw new ServiceException(": We have run out of this coupon!");
            } else throw new ServiceException(" : You have already purchased this coupon!");
        } catch (ServiceException e) {
            System.out.println(TextColors.ANSI_RED + "Exception: " + e.getMessage() + "" + TextColors.ANSI_RESET);
            return "" + coupon.getTitle()+""+ e.getMessage();
        }
    }

    public ArrayList<Coupon> getCustomerCoupons() {
        return couponRepository.allCustomerCoupons(customerId);
    }

    public ArrayList<Coupon> getCustomerCoupons(Category category) {
        return couponRepository.allCustomerCouponsByCategory(customerId, category.ordinal());
    }

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) {
        return couponRepository.allCustomerCouponsBelowMaxPrice(customerId, maxPrice);
    }

    public Customer getCustomerDetails() {
        return customer;
    }

    public Customer getCustomerByEmailAndPassword(String email, String password) {
        return customerRepository.getByEmailAndPassword(email, password);
    }

    public void setCustomerAndCustomerID(Customer customer, int customerId) {
        this.customerId = customerId;
        this.customer = customer;
    }

    public ArrayList<Coupon> displayAllCoupons(){
        List<Coupon> coupons = couponRepository.findAll();
        System.out.println(coupons);
        return (ArrayList<Coupon>) coupons;
    }
}
