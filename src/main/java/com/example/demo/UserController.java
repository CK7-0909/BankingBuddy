package com.example.demo;

//Test the repository

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController // Methods returns data to http response
@RequestMapping("/users") // Specifies base URL for all endpoints /users in this case.
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add") // Specifies method mappings to handle requests
    public User addUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String dob) {
        Date dobDate = java.sql.Date.valueOf(dob);
        return userService.createUser(firstName, lastName, dobDate);
    }

    @GetMapping("/getByapplicationId")
    public User getUserByApplicationId(@RequestParam int applicationId) {
        return userService.getUserbyapplicantId(applicationId);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User updateUser) { // use @RequestBody User user to update info in JSON
        return userService.updateUser(id, updateUser);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
