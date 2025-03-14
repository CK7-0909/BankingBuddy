package com.example.demo;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Date;

@Service // Basically this is used for Business logic so that we keep the database info securely in repo(database access) and request handling in controller.
public class UserService {

   // @Autowired // This basically ties up the program together to perform dependency injection
    private final Repository repository;

    public UserService(Repository repository) {
        this.repository = repository;
    }

    // Used for PostMapping
    public User createUser(String firstname, String lastname, String dob, double bankBalance, String country, int creditAge) {
        User user = new User();
        user.setfirstname(firstname);
        user.setlastname(lastname);
        user.setdob(dob);
        user.setBankBalance(bankBalance);
        user.setcountry(country);
        user.setcreditAge(creditAge);
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

    // Add functionality
//    public class checkDuplicate(Optional<Integer> applicationId, Optional<String> firstName, Optional<String> lastName, Optional<String> dob) {
//
  //    }

    // Temporarily using bankBalance to test this function
    public User updateUserCreditScore(int applicationId, double bankBalance) {
        User existingUser = repository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("user not found with ID: " + applicationId));

        existingUser.setBankBalance(bankBalance);
        return repository.save(existingUser);
    }
}
