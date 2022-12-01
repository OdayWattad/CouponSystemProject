/**
 * AdminService includes all the functions that the user(admin) can do in this program. The service uses the repositories in order to connect
 * to the DataBase.
 *
 * The AdminService throws SQL and Interrupted exceptions - to be handled later in the testAll.
 *
 * The functions and their uses:
 *    1. Login: The user needs to log in before he can do any functions. Here the program checks if the details he/she entered are correct.
 *    2. addCompany: The admin can add a new company to the dataBase. However, he can't add an already existing company. if he does,
 * The program will throw a custom exception (ServiceException).
 *    3. updateCompany: The admin is able to update the company details except for its name. The program would throw a
 * custom exception (ServiceException).
 *    4. deleteCompany: The admin can delete a company, and automatically it will delete its coupons entirely from the dataBase.
 *    5. getAllCompanies: The admin is able to see all the companies details except for the couponList with this function.
 *    6. getOneCompany: The admin is able to see all the details (excluding the couponList) of one company through its id.
 *    7. addCustomer: The admin can add a new customer to the dataBase. However, he can't add a customer with an email similar to
 * another from the dataBase.If he does,The program will throw a custom exception (ServiceException).
 *    8. update Customer: The admin is able to update all the Customer's details.
 *    9. deleteCustomer: The admin is able to delete a customer and his coupons from the dataBase.
 *    10. getAllCustomers: The admin is able to see all the customers' details except for the couponList with this function.
 *    11. getOneCustomer: The admin is able to see all the details (excluding the couponList) of one customer through the id.
 */
package com.CouponSystemStage2.CouponSystem.Services;

import com.CouponSystemStage2.CouponSystem.DesignColors.TextColors;
import com.CouponSystemStage2.CouponSystem.Entities.Company;
import com.CouponSystemStage2.CouponSystem.Entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
@Scope("prototype")
public class AdminService extends ClientService {
    private final String email = "admin@admin.com";
    private final String password = "admin123";


    public boolean login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password))
            return true;
        return false;
    }

    public int addCompany(Company company) {
        if (!companyRepository.existsByNameAndEmail(company.getName(), company.getEmail())) {
           Company addedCompany =  companyRepository.save(company);
            return addedCompany.getId();
        } else
            try {
                throw new ServiceException("Company already exists!\nCannot be added!");
            } catch (ServiceException e) {
                System.out.println(TextColors.ANSI_RED + "Exception: " + e.getMessage() + "" + TextColors.ANSI_RESET);
                return 0;
            }
    }

    public boolean updateCompany(Company company) {
        Company companyFromDB = companyRepository.findById(company.getId()).get();
        if (company.getName().equals(companyFromDB.getName())) {
            companyRepository.updateCompany(company);
            return true;
        } else
            try {
                throw new ServiceException("Cannot update with different name!");
            } catch (ServiceException e) {
                System.out.println(TextColors.ANSI_RED + "Exception: " + e.getMessage() + "" + TextColors.ANSI_RESET);
                return false;
            }

    }

    public void deleteCompany(int companyID) {
            companyRepository.deleteById(companyID);
    }

    public ArrayList<Company> getAllCompanies() {
        return (ArrayList<Company>) companyRepository.findAll();
    }

    public Company getOneCompany(int companyID) {
        return companyRepository.findById(companyID).get();
    }

    public int addCustomer(Customer customer) {
        if (!customerRepository.existsByEmail(customer.getEmail())) {
            Customer customerForId = customerRepository.save(customer);
            return customerForId.getId() ;
        } else {
            try {
                throw new ServiceException("Customer already exists!");
            } catch (ServiceException e) {
                System.out.println(TextColors.ANSI_RED + "Exception: " + e.getMessage() + "" + TextColors.ANSI_RESET);
                return 0;
            }
        }
    }

    public void updateCustomer(Customer customer) {
        customerRepository.updateCustomer(customer);
    }

    public void deleteCustomer(int customerID) {
        customerRepository.deleteCustomerFromPurchaseList(customerID);
        customerRepository.deleteById(customerID);
    }

    public ArrayList<Customer> getAllCustomers() {
        return (ArrayList<Customer>) customerRepository.findAll();
    }

    public Customer getOneCustomer(int customerId){
       return customerRepository.findById(customerId).get();
    }


}

