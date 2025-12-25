package com.minigoogle.processingservice.service;

import com.minigoogle.processingservice.model.IndexedPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Service that consumes messages from Kafka and indexes them into Elasticsearch.
 */
@Service
@Slf4j
public class KafkaConsumerService {

    private final ElasticsearchOperations elasticsearchOperations;

    public KafkaConsumerService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * Listens to the 'web-pages' topic.
     * When a message arrives, it's automatically converted to an IndexedPage object.
     */
    @KafkaListener(topics = "web-pages", groupId = "processing-group")
    public void consume(IndexedPage page) {
        log.info("Received page from Kafka for processing: {}", page.getUrl());

        try {
            // Saving the document to Elasticsearch
            elasticsearchOperations.save(page);
            log.info("Successfully indexed page in Elasticsearch: {}", page.getUrl());
        } catch (Exception e) {
            log.error("Failed to index page in Elasticsearch: {}", e.getMessage());
        }
    }
}