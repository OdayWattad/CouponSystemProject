/**
 * The loginManager is responsible for checking the correctness of the login details of the client.
 * Also, it is responsible for returning ClientService object that fits the user's clientType.
 * The loginManager makes use of the functions: getCompanyByEmailAndPassword and getCustomerByEmailAndPassword
 * in order to get the instance and id of the client by using the email and password in order to assert the client in the dataBase.
 * The ctx is injected here to make use of multiple service instances.
 * <p>
 * <p>
 * It throws custom Exceptions when entered wrong details: (LoginException) with proper message to the user.
 */
package com.CouponSystemStage2.CouponSystem.Login;

import com.CouponSystemStage2.CouponSystem.DesignColors.TextColors;
import com.CouponSystemStage2.CouponSystem.Entities.Company;
import com.CouponSystemStage2.CouponSystem.Entities.Customer;
import com.CouponSystemStage2.CouponSystem.Services.*;
import com.CouponSystemStage2.CouponSystem.Test.TestAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.context.ConfigurableApplicationContext;


@Component
@Scope("singleton")
public class LoginManager {
    @Autowired
    ApplicationContext ctx;


    public enum ClientType {ADMINISTRATOR, COMPANY, CUSTOMER}

    static LoginManager instance = new LoginManager();


    public static LoginManager getInstance() {
        return instance;
    }


    public ClientService Login(String email, String password, ClientType clientType) {
        System.out.println("There is a problem?! From LoginManager");
        switch (clientType) {
            case COMPANY: {
                CompanyService companyService = ctx.getBean(CompanyService.class);
                if (companyService.login(email, password)) {
                    Company company = companyService.getCompanyByEmailAndPassword(email, password);
                    companyService.setCompanyAndCompanyID(company, company.getId());
                    System.out.println(TextColors.ANSI_YELLOW + "Company has logged-in successfully!" + TextColors.ANSI_RESET);
                    return companyService;
                } else {
                    try {
                        throw new LoginException("Either email or password does not exist for this company");
                    } catch (LoginException e) {
                        System.out.println(TextColors.ANSI_RED + "Exception: " + e.getMessage() + "" + TextColors.ANSI_RESET);
                    }
                }
            }
            break;

            case CUSTOMER: {
                CustomerService customerService = ctx.getBean(CustomerService.class);
                if (customerService.login(email, password)) {
                    Customer customer = customerService.getCustomerByEmailAndPassword(email, password);
                    customerService.setCustomerAndCustomerID(customer, customer.getId());
                    System.out.println(TextColors.ANSI_YELLOW + "Customer has logged-in successfully!" + TextColors.ANSI_RESET);
                    return customerService;
                } else {
                    try {
                        throw new LoginException("Either email or password does not exist for this customer");
                    } catch (LoginException e) {
                        System.out.println(TextColors.ANSI_RED + "Exception: " + e.getMessage() + "" + TextColors.ANSI_RESET);
                    }
                }
            }
            break;

            case ADMINISTRATOR: {
                AdminService adminService = ctx.getBean(AdminService.class);
                if (adminService.login(email, password)) {
                    System.out.println(TextColors.ANSI_YELLOW + "Admin has logged-in successfully!" + TextColors.ANSI_RESET);
                    return adminService;
                } else {
                    try {
                        throw new LoginException("Either email or password does not exist");
                    } catch (LoginException e) {
                        System.out.println(TextColors.ANSI_RED + "Exception: " + e.getMessage() + "" + TextColors.ANSI_RESET);
                    }
                }
            }
        }
        return null;
    }
}