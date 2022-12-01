/**
 * The CompanyRepository holds the functions that will be used by the Services. These functions form a connection to the dataBase
 * by JPA and Hibernate.
 * <p>
 * ABOUT THE FUNCTIONS AND THEIR USES:
 * existsByEmailAndPassword  - this functions check if the company actually exists in the database by email and password.
 * existsByNameAndEmail - this function checks if the company actually exists in the database by name and email.
 * getCompanyByEmailAndPassword - the function returns a company object by the email and password.
 * updateCompany - This function updates the new details that you changed to the current company object in the DataBase.
 */
package com.CouponSystemStage2.CouponSystem.Repositories;

import com.CouponSystemStage2.CouponSystem.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByNameAndEmail(String name, String email);

    @Transactional
    @Modifying
    @Query(value = "update companies as c set c.email= :#{#company.email} ,  c.password= :#{#company.password}   where c.id= :#{#company.id}", nativeQuery = true)
    void updateCompany(@Param("company") Company company);

    Company getByEmailAndPassword(String email, String password);

}
