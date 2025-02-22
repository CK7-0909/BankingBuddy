package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Service // Basically this is used for Business logic so that we keep the database info securely in repo(database access) and request handling in controller.
public class UserServiceTest {

    @Autowired // This basically ties up the program together to perform dependency injection
    private  Repository repository;

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
        User user = new User();
        user.setBankBalance(bankBalance);
        return repository.save(user);
    }

    public static double calculateFinancialScore(int creditAge, double bankBalance, String dob) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dob, format);
        LocalDate now = LocalDate.now();
        int age = now.getYear() - localDate.getYear();

        // Score logic is based on heuristics but not offical equation
        return (creditAge * 5) + (bankBalance/1000) + (age*2);
    }

    // Unit Testing
    @Test
    void testFinancialScore() {
        String dob = "2000-01-01"; // Fixed date testing
        double bankBalance = 20.0;
        int creditAge = 4;
        double calculateFinancialScore = User.calculateFinancialScore(creditAge, bankBalance, dob);
        double expected = 70.02;
        // Assert to see if test matches
        assertEquals(expected, calculateFinancialScore, "Test failed");
    }
    @Test
    void testFinancialScoreForFuture() {
        String dob = "2050-01-01"; // Fixed date testing
        double bankBalance = 20.0;
        int creditAge = 4;
        double calculateFinancialScore = User.calculateFinancialScore(creditAge, bankBalance, dob);

        // Assert to see if test matches
        assertTrue(calculateFinancialScore < 0, "Test failed");
    }
}
