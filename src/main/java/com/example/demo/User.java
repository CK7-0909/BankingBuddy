package com.example.demo;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "userinfo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String firstname;
    private String lastname;
    private String dob;
    private double bankBalance;
    private String country;
    private int creditAge;

    public User() {
    }

    public User(String firstName, String lastName, String dob, double bankBalance, String country, int creditAge) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.dob = dob;
        this.bankBalance = bankBalance;
        this.country = country;
        this.creditAge = creditAge;
    }

    public String getfirstname() {
        return firstname;
    }
    public void setfirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getlastname() {
        return lastname;
    }
    public void setlastname(String lastname) {
        this.lastname = lastname;
    }
    public String getdob() {
        return dob;
    }
    public void setdob(String dob) {
        this.dob = dob;
    }
    public double getBankBalance() {
        return bankBalance;
    }
    public void setBankBalance(double bankBalance) {
        this.bankBalance = bankBalance;
    }
    public String getcountry() {
        return country;
    }
    public void setcountry(String country) {
        this.country = country;
    }
    public int getcreditAge() {
        return creditAge;
    }
    public void setcreditAge(int creditAge) {
        this.creditAge = creditAge;
    }

}
