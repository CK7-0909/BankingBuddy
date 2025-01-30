package com.example.demo;

//Test the repository

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController // Methods returns data to http response
@RequestMapping("/users") // Specifies base URL for all endpoints /users in this case.
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add") // Specifies method mappings to handle requests
    public User addUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String dob) {
        return userService.createUser(firstName, lastName, dob);
    }

//    @GetMapping("/getByapplicationId")
//    public User getUserByApplicationId(@RequestParam int applicationId) {
//        return userService.getUserbyapplicantId(applicationId);
//    }
//
//    @PutMapping("/update/{id}")
//    public User updateUser(@PathVariable int id, @RequestBody User updateUser) { // use @RequestBody User user to update info in JSON
//        return userService.updateUser(id, updateUser);
//    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @Autowired
    public KafkaProducer kafkaProducer;
    @Autowired
    public ExcelReader excelReader;
    @PostMapping("/kafka")
    public void kafkaMessage(@RequestBody MultipartFile file) {
        try {
            List<User> users = excelReader.readExcelData(file);
            for (User user : users) {
                kafkaProducer.sendMessage(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
