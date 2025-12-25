package com.minigoogle.processingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Represents the document structure to be stored in Elasticsearch.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "web_pages") // The name of the index in Elasticsearch
public class IndexedPage {

    @Id // The URL will serve as the unique document ID
    private String url;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;
}