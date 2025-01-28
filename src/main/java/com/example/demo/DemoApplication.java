package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

import static com.example.demo.KafkaStreamProcessor.sendToKafka;

@SpringBootApplication
public class DemoApplication {
	@Autowired
	private ExcelReader excelReader;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	public void runAfterStartup() {
		// This method will run once the Spring Boot application starts
		try {
			List<User> users = excelReader.readExcelData();
			String bootstrapServers = "localhost:9092";
			String topic = "input-topic";
			for (User user : users) {
				sendToKafka(user, bootstrapServers, topic);
			}

			// Set up the Kafka Processor
			KafkaStreamProcessor processor = new KafkaStreamProcessor("inputTopic", "outputTopic", "KafkaStreams", "localhost:9092");
			processor.start();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
