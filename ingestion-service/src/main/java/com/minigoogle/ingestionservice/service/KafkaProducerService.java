package com.minigoogle.ingestionservice.service;

import com.minigoogle.ingestionservice.model.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service responsible for sending web page data to Kafka topics.
 */
@Service
public class KafkaProducerService {

    // Define the topic name where web pages will be sent
    private static final String TOPIC = "web-pages";

    // KafkaTemplate is a Spring helper that simplifies Kafka operations
    // Key: String (URL), Value: PageRequest (The DTO we created)
    private final KafkaTemplate<String, PageRequest> kafkaTemplate;

    /**
     * Constructor Injection is the recommended way to inject dependencies in Spring.
     * It makes the code easier to test and ensures the com.minigoogle.ingestionservice.com.minigoogle.processingservice.service is not created without the template.
     */
    public KafkaProducerService(KafkaTemplate<String, PageRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends a page event to the Kafka topic.
     * @param page The page data received from the API.
     */
    public void sendPageEvent(PageRequest page) {
        /*
         * Sending the message:
         * 1. TOPIC: The destination "channel"
         * 2. page.getUrl(): The Key. Kafka uses this to ensure all updates for the same URL
         * go to the same partition (Order Guarantee).
         * 3. page: The actual data payload (Value).
         */
        kafkaTemplate.send(TOPIC, page.getUrl(), page);

        // Log the action for debugging (In production, use a Logger instead of System.out)
        System.out.println("Message sent to Kafka topic [" + TOPIC + "] with Key (URL): " + page.getUrl());
    }
}