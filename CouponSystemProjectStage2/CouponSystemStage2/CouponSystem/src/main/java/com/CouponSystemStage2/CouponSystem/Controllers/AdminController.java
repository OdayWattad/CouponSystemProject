package com.CouponSystemStage2.CouponSystem.Controllers;

import com.CouponSystemStage2.CouponSystem.Entities.Company;
import com.CouponSystemStage2.CouponSystem.Entities.Customer;
import com.CouponSystemStage2.CouponSystem.Login.LoginManager;
import com.CouponSystemStage2.CouponSystem.Services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("adminApi")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class AdminController extends ClientController {

    static LoginManager.ClientType type = LoginManager.ClientType.ADMINISTRATOR;

    @GetMapping("/login/{email}/{password}")
    @Override
    public ResponseEntity<String> login(@PathVariable("email") String email, @PathVariable("password") String password) throws Exception {//http://localhost:8080/adminApi/login/admin@admin.com/admin
        AdminService adminService = (AdminService) loginManager.Login(email, password, LoginManager.ClientType.ADMINISTRATOR);
        if (adminService == null) {
            return new ResponseEntity<>("Invalid User", HttpStatus.BAD_REQUEST);
        }
        String token = tokenManager.createToken(adminService, type);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader("token") String token, @RequestBody Company company) {  // http://localhost:8080/adminApi/addCompany
        AdminService adminService = (AdminService) tokenManager.getService(token, type);
        if (adminService == null) {
            return new ResponseEntity<>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        int id = adminService.addCompany(company);
        if (id > 0) {
            return new ResponseEntity<Integer>(id, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Either email or name already exists", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updateCompany")
    public ResponseEntity<String> updateCompany(@RequestHeader("token") String token, @RequestBody Company company) {  // http://localhost:8080/adminApi/updateCompany
        AdminService adminService = (AdminService) tokenManager.getService(token, type);
        if (adminService == null) {
            return new ResponseEntity<>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        adminService.updateCompany(company);
        return new ResponseEntity<>("Company Was Updated", HttpStatus.OK);
    }

    @DeleteMapping("/deleteCompany/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") int id, @RequestHeader("token") String token) { // http://localhost:8080/adminApi/deleteCompany/{id}
        AdminService adminService = (AdminService) tokenManager.getService(token, type);
        if (adminService == null) {
            return new ResponseEntity<>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        adminService.deleteCompany(id);
        return new ResponseEntity<>("Company Was Deleted Successfully!", HttpStatus.OK);


    }

    @GetMapping("/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader("token") String token) { // http://localhost:8080/adminApi/getAllComapnies
        AdminService adminService = (AdminService) tokenManager.getService(token, type);
        if (adminService == null) {
            return new ResponseEntity<String>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ArrayList<Company>>(adminService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/getOneCompany/{id}")
    public ResponseEntity<?> getOneCompany(@RequestHeader("token") String token, @PathVariable("id") int id) { // http://localhost:8080/adminApi/getOneCompany/{id}
        AdminService adminService = (AdminService) tokenManager.getService(token, type);
        if (adminService == null) {
            return new ResponseEntity<String>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        Company company = adminService.getOneCompany(id);
        return new ResponseEntity<Company>(company, HttpStatus.OK);

    }

    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader("token") String token, @RequestBody Customer customer) { // http://localhost:8080/adminApi/addCustomer
        AdminService adminService = (AdminService) tokenManager.getService(token, type);
        if (adminService == null) {
            return new ResponseEntity<String>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        int isExist = adminService.addCustomer(customer);
        if (!(isExist == 0)) {
            return new ResponseEntity<Integer>(isExist, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Customer was not added successfully, something went wrong", HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<String> updateCustomer(@RequestHeader("token") String token, @RequestBody Customer customer) { // http://localhost:8080/adminApi/updateCustomer
        AdminService adminService = (AdminService) tokenManager.getService(token, type);
        if (adminService == null) {
            return new ResponseEntity<>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        adminService.updateCustomer(customer);
        return new ResponseEntity<>("Customer Was Updated Successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<String> deleteCustomer(@RequestHeader("token") String token, @PathVariable("id") int id) { // http://localhost:8080/adminApi/deleteCustomer/{id}
        AdminService adminService = (AdminService) tokenManager.getService(token, type);
        if (adminService == null) {
            return new ResponseEntity<>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        adminService.deleteCustomer(id);
        return new ResponseEntity<>(" Customer Was Deleted Successfully!", HttpStatus.OK);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader("token") String token) { // http://localhost:8080/adminApi/getAllCustomers
        AdminService adminService = (AdminService) tokenManager.getService(token, type);
        if (adminService == null) {
            return new ResponseEntity<String>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ArrayList<Customer>>(adminService.getAllCustomers(), HttpStatus.OK);
    }


    @GetMapping("/getOneCustomer/{id}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader("token") String token, @PathVariable("id") int id) { // http://localhost:8080/adminApi/getOneCustomer/{id}
        AdminService adminService = (AdminService) tokenManager.getService(token, type);
        if (adminService == null) {
            return new ResponseEntity<String>("Service is not found", HttpStatus.BAD_REQUEST);
        }
        Customer customer = adminService.getOneCustomer(id);
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }
}