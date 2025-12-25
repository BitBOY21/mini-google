package com.minigoogle.searchservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // הוסף את ה-Import הזה
import lombok.Data;
import java.util.List;

/**
 * Model representing a web page indexed in Elasticsearch.
 * The annotation ensures that metadata fields like "_class" are ignored during deserialization.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexedPage {
    private String url;
    private String title;
    private String content;
    private List<String> highlights;
}