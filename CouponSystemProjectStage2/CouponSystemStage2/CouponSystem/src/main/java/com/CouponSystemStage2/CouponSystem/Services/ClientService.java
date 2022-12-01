/**
 * The clientService is the Factory of all the possible clients that are able to log in.
 * The login Function  is abstract in order for any class inheriting the ClientService can have the ability to log in.
 * All the repositories (CompanyRepository,CouponRepository and CustomerRepository ) are injected and to be inherited.
 *
 * It throws SQL and Interrupted Exceptions - to be handled later in the testAll
 */
package com.CouponSystemStage2.CouponSystem.Services;

import com.CouponSystemStage2.CouponSystem.Repositories.CompanyRepository;
import com.CouponSystemStage2.CouponSystem.Repositories.CouponRepository;
import com.CouponSystemStage2.CouponSystem.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public abstract class ClientService {
    @Autowired
    protected CompanyRepository companyRepository;

    @Autowired
    protected CouponRepository couponRepository;

    @Autowired
    protected CustomerRepository customerRepository;

    public abstract boolean login(String email, String password);

}
