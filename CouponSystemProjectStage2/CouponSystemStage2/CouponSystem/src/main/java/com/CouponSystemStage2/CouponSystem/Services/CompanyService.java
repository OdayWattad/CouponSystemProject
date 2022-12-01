/**
 * CompanyService includes all the functions that the user(Company) can do in this program. The Service uses the repositories in order to connect
 * to the DataBase.
 *
 * The CompanyService throws SQL and Interrupted exceptions - to be handled later in the testAll.
 *
 * The functions and their uses:
 *    1. Login: The user needs to log in before he can do any functions. Here the program checks if the details he/she entered are correct.
 *    2. getCompanyByEmailAndPassword: Not available to the company. The loginManager uses this function.
 *    3. addCoupon: The company can add a coupon to its couponList and in the database through this function.
 *    4. updateCoupon: The company can update its coupons' details except for the CompanyId. The program would throw a custom exception
 *  (ServiceException).
 *    5. deleteCoupon: The company is able to delete one of its coupons entirely from the database and from the customer who already purchased it.
 *    6. getCompanyCoupons: The company is able to view it's all coupons/ it's coupons sorted by category of their choice
 *    /it's coupons sorted by maxPrice of their choice.
 *    7. getCompanyDetails: The company is able to view it's all details (including the couponList).
 */
package com.CouponSystemStage2.CouponSystem.Services;


import com.CouponSystemStage2.CouponSystem.DesignColors.TextColors;
import com.CouponSystemStage2.CouponSystem.Entities.Category;
import com.CouponSystemStage2.CouponSystem.Entities.Company;
import com.CouponSystemStage2.CouponSystem.Entities.Coupon;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Scope("prototype")
public class CompanyService extends ClientService {


    private int companyId;

    private Company company;

    public boolean login(String email, String password) {
        return (companyRepository.existsByEmailAndPassword(email, password));
    }

    public boolean addCoupon(Coupon coupon) {
        if (!couponRepository.existsByCompanyAndTitle(this.company, coupon.getTitle())) {
            coupon.setCompany(this.company);
            couponRepository.save(coupon);
            return true;
        } else {
            try {
                throw new ServiceException("This coupon already exists!");
            } catch (ServiceException e) {
                System.out.println(TextColors.ANSI_RED + "Exception: " + e.getMessage() + "" + TextColors.ANSI_RESET);
                return false;
            }
        }
    }


    public void updateCoupon(Coupon coupon) {
//        if (this.companyId == coupon.getCompany()) {
            couponRepository.updateCoupon(coupon);
//        } else
//            try {
//                throw new ServiceException("You can't update the companyID");
//            } catch (ServiceException e) {
//                System.out.println(TextColors.ANSI_RED + "Exception: " + e.getMessage() + "" + TextColors.ANSI_RESET);
//            }

    }

    public void deleteCoupon(int couponID) {
        couponRepository.deleteById(couponID);
    }

    public ArrayList<Coupon> getCompanyCoupons() {
        return couponRepository.findAllByCompany(this.company);
    }

    public ArrayList<Coupon> getCompanyCoupons(Category category) {
        return couponRepository.findAllByCompanyAndCategory(this.company, category);
    }


    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
        return couponRepository.findAllByCompanyAndPriceLessThan(this.company, maxPrice);
    }

    public Company getCompanyDetails() {
        return this.company;
    }

    public void setCompanyAndCompanyID(Company company, int companyId) {
        this.companyId = companyId;
        this.company = company;
    }

    public Company getCompanyByEmailAndPassword(String email, String password) {
        return companyRepository.getByEmailAndPassword(email, password);
    }


}
