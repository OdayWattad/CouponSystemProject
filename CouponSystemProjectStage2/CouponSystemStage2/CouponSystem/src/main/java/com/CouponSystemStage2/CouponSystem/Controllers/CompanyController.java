package com.CouponSystemStage2.CouponSystem.Controllers;

import com.CouponSystemStage2.CouponSystem.Entities.Category;
import com.CouponSystemStage2.CouponSystem.Entities.Company;
import com.CouponSystemStage2.CouponSystem.Entities.Coupon;
import com.CouponSystemStage2.CouponSystem.Login.LoginManager;
import com.CouponSystemStage2.CouponSystem.Services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("companyApi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyController extends ClientController {
    static LoginManager.ClientType type = LoginManager.ClientType.COMPANY;

    @Override
    @GetMapping("/login/{email}/{password}") // http://localhost:8080/companyApi/login/{email}/{password}
    @ResponseBody
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        CompanyService service = (CompanyService) loginManager.Login(email, password, LoginManager.ClientType.COMPANY);
        if (service == null) {
            return new ResponseEntity<String>("Invalid User", HttpStatus.BAD_REQUEST);
        }
        String token = tokenManager.createToken(service, type);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/addCoupon") // http://localhost:8080/companyApi/addCoupon
    @ResponseBody
    public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon, @RequestHeader("token") String token) {
        CompanyService companyService = (CompanyService) tokenManager.getService(token, type);
        if (companyService == null) {
            return new ResponseEntity<String>("No service is found", HttpStatus.BAD_REQUEST);
        } else {
            if (companyService.addCoupon(coupon)) {
                return new ResponseEntity<String>("The coupon as added", HttpStatus.OK);
            }
            return new ResponseEntity<String>("Already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateCoupon") // http://localhost:8080/companyApi/updateCoupon
    @ResponseBody
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon, @RequestHeader("token") String token) {
        System.out.println(token);
        CompanyService companyService = (CompanyService) tokenManager.getService(token, type);
        if (companyService == null) {
            return new ResponseEntity<String>("No service found", HttpStatus.BAD_REQUEST);
        } else {
            companyService.updateCoupon(coupon);
            return new ResponseEntity<String>("THE COUPON WAS UPDATED", HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteCoupon/{couponId}") // http://localhost:8080/companyApi/deleteCoupon/{couponId}
    @ResponseBody
    public ResponseEntity<?> deleteCoupon(@PathVariable int couponId, @RequestHeader("token") String token) {
        CompanyService companyService = (CompanyService) tokenManager.getService(token, type);
        if (companyService == null) {
            return new ResponseEntity<String>("No service found", HttpStatus.BAD_REQUEST);
        } else {
            companyService.deleteCoupon(couponId);
            return new ResponseEntity<String>("The coupon was deleted", HttpStatus.OK);
        }

    }

    @GetMapping("/getCompanyCoupons") // http://localhost:8080/companyApi/getCompanyCoupons
    @ResponseBody
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader("token") String token) {
        CompanyService companyService = (CompanyService) tokenManager.getService(token, type);
        if (companyService == null) {
            return new ResponseEntity<String>("No service found", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<ArrayList<Coupon>>(companyService.getCompanyCoupons(), HttpStatus.OK);
        }
    }

    @GetMapping("/getCompanyCouponsByCategory/{category}")
    // http://localhost:8080/companyApi/getCompanyCouponsByCategory
    @ResponseBody
    public ResponseEntity<?> getCompanyCouponsByCategory(@PathVariable String category, @RequestHeader("token") String token) {
        CompanyService companyService = (CompanyService) tokenManager.getService(token, type);
        System.out.println("category is " + category);
        System.out.println("category is " + Category.valueOf(category));
        if (companyService == null) {
            return new ResponseEntity<String>("No service is found", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<ArrayList<Coupon>>(companyService.getCompanyCoupons(Category.valueOf(category)), HttpStatus.OK);
        }

    }

    @GetMapping("/getCompanyCouponsByMaxPrice/{maxPrice}")
    // http://localhost:8080/companyApi/getCompanyCouponsByMaxPrice
    @ResponseBody
    public ResponseEntity<?> getCompanyCouponsByMaxPrice(@PathVariable String maxPrice, @RequestHeader("token") String token) {
        CompanyService companyService = (CompanyService) tokenManager.getService(token, type);
        System.out.println("Max Price is: " + maxPrice);
        System.out.println("Max Price is: " + Integer.parseInt(maxPrice));
        if (companyService == null) {
            return new ResponseEntity<String>("No service is found", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<ArrayList<Coupon>>(companyService.getCompanyCoupons(Integer.parseInt(maxPrice)), HttpStatus.OK);
        }
    }

    @GetMapping("/getCompanyDetails") // http://localhost:8080/companyApi/getCompanyDetails
    @ResponseBody
    public ResponseEntity<?> getCompanyDetails(@RequestHeader("token") String token) {
        CompanyService companyService = (CompanyService) tokenManager.getService(token, type);
        if (companyService == null) {
            return new ResponseEntity<String>("No company service", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Company>(companyService.getCompanyDetails(), HttpStatus.OK);
        }
    }
}
