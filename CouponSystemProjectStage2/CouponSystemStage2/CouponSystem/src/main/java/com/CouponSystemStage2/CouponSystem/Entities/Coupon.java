/**
 * The Coupon is a class that we create objects from in the program
 * The fields of the Coupon are: id, companyId, category, title, description, startDate, endDate, amount, price, image
 */

package com.CouponSystemStage2.CouponSystem.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {
    public Coupon(Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @Column(name = "category_id")
    private Category category;

    private String title;

    private String description;
    @NonNull
    @Column(name = "start_date")
    private Date startDate;
    @NonNull
    @Column(name = "end_date")
    private Date endDate;
    private int amount;
    private double price;
    private String image;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "coupons_vs_customers",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> customerList = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    public int getCompany() {
        return company.getId();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "category = " + category + ", " +
                "title = " + title + ", " +
                "description = " + description + ", " +
                "startDate = " + startDate + ", " +
                "endDate = " + endDate + ", " +
                "amount = " + amount + ", " +
                "price = " + price + ", " +
                "image = " + image + " ,  id = " + id + ", company_id =" + company + ")";
    }
}