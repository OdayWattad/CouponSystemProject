/**
 * The Customer is a class that we create objects from in the program
 * The fields of the Customer are: id, firstName, lastName, email, password, and coupons(arraylist)
 * coupons is the only field that is not used in the dataBase in the SQL.
 */
package com.CouponSystemStage2.CouponSystem.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @JsonIgnore
    @ManyToMany(mappedBy = "customerList", fetch = FetchType.LAZY)
    private List<Coupon> couponList = new ArrayList<>();


    public Customer(String firstName, String lastName, String email, String password, List<Coupon> couponList) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.couponList = couponList;
    }

    public void addCoupon(Coupon coupon) {
        couponList.add(coupon);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password +
                '}';
    }
}
