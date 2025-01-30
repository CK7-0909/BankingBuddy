package com.example.demo;

//Test the repository

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController // Methods returns data to http response
@RequestMapping("/users") // Specifies base URL for all endpoints /users in this case.
public class UserController {
    @Autowired
    private UserService userService;

//    @PostMapping("/add") // Specifies method mappings to handle requests
//    public User addUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String dob) {
//        return userService.createUser(firstName, lastName, dob);
//    }

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

    private int excelReadCount;
    @Autowired
    public KafkaProducer kafkaProducer;
    @Autowired
    public ExcelReader excelReader;
    @PostMapping("/uploadToKafka")
    public void kafkaMessage(@RequestBody MultipartFile file) {
        try {
            List<User> users = excelReader.readExcelData(file);
            excelReadCount = users.size();
            System.out.println(excelReadCount);
            for (User user : users) {
                kafkaProducer.sendMessage(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    List<User> messages = new ArrayList<>();
    @KafkaListener(topics = "DemoKafka", groupId = "DemoKafka")
    public void consume(User user) {
        //We could do processing here directly before adding them to our list or database
        //userService.createUser(user.getfirstname(), user.getlastname(), user.getdob(), user.getBankBalance(), user.getcountry(), user.getcreditAge());
        messages.add(user);
    }
    @GetMapping("/kafkaConsume")
    public List<User> getMessages() {
        return messages;
    }

    @GetMapping("/excelReadCount")
    public int excelReadCount() {
        return excelReadCount;
    }




}
