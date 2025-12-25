package com.minigoogle.ingestionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a web page to be indexed.
 */
@Data // Generates Getters, Setters, equals, hashCode, and toString methods automatically
@AllArgsConstructor // Generates a constructor with all fields as arguments
@NoArgsConstructor // Generates a default constructor with no arguments
public class PageRequest {

    // The unique URL of the web page (will be used as the Kafka message key)
    private String url;

    // The title of the page to be displayed in search results
    private String title;

    // The raw text content of the page for indexing and searching
    private String content;
}