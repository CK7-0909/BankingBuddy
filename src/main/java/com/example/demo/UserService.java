package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Date;

@Service // Basically this is used for Business logic so that we keep the database info securely in repo(database access) and request handling in controller.
public class UserService {

    @Autowired // This basically ties up the program together to perform dependency injection
    private  Repository repository;

    // Used for PostMapping
    public User createUser(String firstname, String lastname, Date dob) {
        User user = new User();
        // Will mysql automatically assign an id?
        user.setfirstname(firstname);
        user.setlastname(lastname);
        user.setdob(dob);
        return repository.save(user); // Save is a function part of the JPA repository
    }

    //Used for GetMapping
    public User getUserbyapplicantId(Integer applicantId) {
        return repository.findById(applicantId).orElse(null); // Need to convert to long or change find by
    }

    //Used for PutMapping
    public User updateUser(Integer applicationId, User updateUser) {
        User existingUser = repository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("user not found with ID: " + applicationId));

        existingUser.setfirstname(updateUser.getfirstname());
        existingUser.setlastname(updateUser.getlastname());
        existingUser.setdob(updateUser.getdob());

        return repository.save(existingUser);
    }

    public void deleteUser(Integer applicationId) {
        repository.deleteById(applicationId);
    }
    service
}