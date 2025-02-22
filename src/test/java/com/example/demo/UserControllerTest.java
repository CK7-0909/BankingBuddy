package com.example.demo;

//Test the repository

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static com.example.demo.User.calculateFinancialScore;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RequestMapping("/users") // Specifies base URL for all endpoints /users in this case.
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    //private UserServiceTest userServiceTest;

    @Mock
    private UserServiceTest userServiceTest;

    @InjectMocks
    private UserController userController;



//    @PostMapping("/add") // Specifies method mappings to handle requests
//    public User addUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String dob) {
//        return mockMvc.createUser(firstName, lastName, dob, 0.0, null, 0); // To create an account we don't need the last 3 fields
//    }
//
//    @GetMapping("/getByapplicationId")
//    public User getUserByApplicationId(@RequestParam int applicationId) {
//        return mockMvc.getUserbyapplicantId(applicationId);
//    }
//
//    @PutMapping("/update/{id}")
//    public User updateUser(@PathVariable int id, @RequestBody User updateUser) { // use @RequestBody User user to update info in JSON
//        return mockMvc.updateUser(id, updateUser);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public void deleteUser(@PathVariable int id) {
//        mockMvc.deleteUser(id);
//    }

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

    @Test
    void testCreditScore() throws Exception {
        String dob = "2000-01-01";
        double bankBalance = 20.0;
        int creditAge = 4;
        double expectedScore = 70.02;

        // Mock the service method
        Mockito.when(userServiceTest.calculateFinancialScore(creditAge, bankBalance, dob))
                        .thenReturn(expectedScore);

        mockMvc.perform(get("/creditScore/{creditAge}/{bankBalance}/{dob}", 4, 20.0, "2000-01-01"))
                .andExpect(status().isOk()) // Expect HTTP 200 is ok
                .andExpect(content().string("70.02"));
    }
}
