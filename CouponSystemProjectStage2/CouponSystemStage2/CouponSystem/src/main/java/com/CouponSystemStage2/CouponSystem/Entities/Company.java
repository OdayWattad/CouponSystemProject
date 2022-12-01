/**
 * The company is a class that we create objects from in the program
 * The fields of the company are: id, name, email, password, and coupons(arraylist)
 * coupons is the only field that is not used in the dataBase in the SQL.
 */

package com.CouponSystemStage2.CouponSystem.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    private List<Coupon> couponList = new ArrayList<>();

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}