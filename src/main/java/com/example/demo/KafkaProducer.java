package com.example.demo;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@Configuration
public class KafkaProducer {
    private static final String TOPIC = "DemoKafka";

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage(User user) {
        //log.info("order of object: " + user);
        kafkaTemplate.send(TOPIC, UUID.randomUUID().toString(), user);
    }
}
