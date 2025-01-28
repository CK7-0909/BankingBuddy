package com.example.demo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;

public class KafkaStreamProcessor {

    private final KafkaStreams streams; // Allows fo flexible usage

    KafkaStreamProcessor(String inputTopic, String outputTopic, String applicationId, String bootstrapServers) {
        //Define stream properties
        Properties property = new Properties();
        property.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        property.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        property.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        property.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        // Receives initial data to transfer
        KStream<String, String> inputstream = builder.stream(inputTopic);

        //We can process data here but nothing for now

        //Send the process data to Kafka output topic
        inputstream.to(outputTopic, Produced.with(Serdes.String(), Serdes.String()));

        // Create instance of KafkaStreams
        this.streams = new KafkaStreams(builder.build(), property);

    }

    public void start() {
        this.streams.start();
    }

    public void stop() {
        this.streams.close();
    }

    public static void sendToKafka(User user, String bootstrapServers, String topic) {
        // Kafka Producer Configurations
        Properties property = new Properties();
        property.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        property.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        property.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Create Kafka producer
        Producer<String, String> producer = new KafkaProducer<>(property);
        try {
            // Serialize to json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(user);

            //Send customer data as json to Kafka
            producer.send(new ProducerRecord<>(topic, "key1", json));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}




