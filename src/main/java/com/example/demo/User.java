package com.example.demo;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "userinfo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicationId")
    private Integer applicationId;
    private String firstname;
    private String lastname;
    private Date dob;

    public int getapplicationId() {
        return applicationId;
    }
//    public void setapplicationId(int applicationId) {
//        this.applicationId = applicationId;
//    }
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
    public Date getdob() {
        return dob;
    }
    public void setdob(Date dob) {
        this.dob = dob;
    }
}
