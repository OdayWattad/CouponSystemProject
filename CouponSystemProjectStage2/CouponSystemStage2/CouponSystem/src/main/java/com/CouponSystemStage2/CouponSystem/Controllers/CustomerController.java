package com.CouponSystemStage2.CouponSystem.Controllers;

import com.CouponSystemStage2.CouponSystem.Entities.Category;
import com.CouponSystemStage2.CouponSystem.Entities.Coupon;
import com.CouponSystemStage2.CouponSystem.Entities.Customer;
import com.CouponSystemStage2.CouponSystem.Login.LoginManager;
import com.CouponSystemStage2.CouponSystem.Repositories.CouponRepository;
import com.CouponSystemStage2.CouponSystem.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("customerApi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController extends ClientController {
    static LoginManager.ClientType type = LoginManager.ClientType.CUSTOMER;

    @Autowired
    protected CouponRepository couponRepository;

    @Override
    @GetMapping("/login/{email}/{password}")
    ResponseEntity<String> login(@PathVariable("email") String email, @PathVariable("password") String password) {  // http://localhost:8080/customerApi/login/{email}/{password}
        CustomerService customerService = (CustomerService) loginManager.Login(email, password, LoginManager.ClientType.CUSTOMER);
        if (customerService == null) {
            return new ResponseEntity<>("Invalid User", HttpStatus.BAD_REQUEST);
        }
        String token = tokenManager.createToken(customerService, type);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader("token") String token) { // http://localhost:8080/customerApi/getCustomerCoupons
        CustomerService customerService = (CustomerService) tokenManager.getService(token, type);
        if (customerService == null) {
            return new ResponseEntity<String>("Service is not found ", HttpStatus.BAD_REQUEST);
        }
        ArrayList<Coupon> coupons = customerService.getCustomerCoupons();
        return new ResponseEntity<ArrayList<Coupon>>(coupons, HttpStatus.OK);
    }

    @GetMapping("/getCustomerCouponsByCategory/{category}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader("token") String token, @PathVariable("category") String category) { // http://localhost:8080/customerApi/getCustomerCouponsByCategory/{category}
        CustomerService customerService = (CustomerService) tokenManager.getService(token, type);
        if (customerService == null) {
            return new ResponseEntity<String>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ArrayList<Coupon>>(customerService.getCustomerCoupons(Category.valueOf(category)), HttpStatus.OK);
    }

    @GetMapping("/getCustomerCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader("token") String token, @PathVariable("maxPrice") double maxPrice) { // http://localhost:8080/customerApi/getCustomerCouponsByMaxPrice/{maxPrice}
        CustomerService customerService = (CustomerService) tokenManager.getService(token, type);
        System.out.println("MaxPrice: " + maxPrice);
        if (customerService == null) {
            return new ResponseEntity<String>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ArrayList<Coupon>>(customerService.getCustomerCoupons(maxPrice), HttpStatus.OK);
    }

    @GetMapping("/getCustomerDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader("token") String token) { // http://localhost:8080/customerApi/getCustomerDetails
        CustomerService customerService = (CustomerService) tokenManager.getService(token, type);
        if (customerService == null) {
            return new ResponseEntity<String>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        Customer customer = customerService.getCustomerDetails();
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    @GetMapping("/displayAllCoupons")
    public ResponseEntity<?> displayAllCoupons(@RequestHeader("token") String token) {
        CustomerService customerService = (CustomerService) tokenManager.getService(token, type);
        if (customerService == null) {
            return new ResponseEntity<String>("Failed Something went wrong ... ", HttpStatus.BAD_REQUEST);
        }
        ArrayList<Coupon> coupons = customerService.displayAllCoupons();
        return new ResponseEntity<ArrayList<Coupon>>(coupons, HttpStatus.OK);
    }

    @PostMapping("/purchaseAllCoupons")
    public ResponseEntity<?> purchaseAllCoupons(@RequestHeader("token") String token, @RequestBody ArrayList<Coupon> couponList) { // http://localhost:8080/customerApi/purcahseCoupon
        CustomerService customerService = (CustomerService) tokenManager.getService(token, type);
        ArrayList<String> messages = new ArrayList<String>();
        for (Coupon coupon : couponList
        ) {
            String message = customerService.purchaseCoupon(coupon);
            messages.add(message);
        }
        if (customerService == null) {
            return new ResponseEntity<String>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ArrayList<String>>(messages, HttpStatus.OK);
    }
}