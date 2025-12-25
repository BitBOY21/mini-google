package com.minigoogle.ingestionservice.controller;

import com.minigoogle.ingestionservice.model.PageRequest;
import com.minigoogle.ingestionservice.service.KafkaProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller that provides endpoints for ingesting new web pages.
 */
@RestController // Marks this class as a RESTful com.minigoogle.ingestionservice.controller
@RequestMapping("/api/v1/pages") // Base URL path for all endpoints in this com.minigoogle.ingestionservice.controller
public class IngestionController {

    private final KafkaProducerService producerService;

    /**
     * Constructor injection for the KafkaProducerService.
     */
    public IngestionController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    /**
     * Receives a web page via POST request and sends it to the processing pipeline.
     * * @param pageRequest The data sent in the request body (automatically mapped from JSON).
     * @return A response entity with a success message and HTTP status 202 (Accepted).
     */
    @PostMapping
    public ResponseEntity<String> ingestPage(@RequestBody PageRequest pageRequest) {
        // Log the incoming request
        System.out.println("Received ingestion request for URL: " + pageRequest.getUrl());

        // Delegate the work to the producer com.minigoogle.ingestionservice.com.minigoogle.processingservice.service
        producerService.sendPageEvent(pageRequest);

        // Return a response to the client.
        // We use 202 Accepted because the processing happens asynchronously in the background.
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Page data received and queued for processing.");
    }
}