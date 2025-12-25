package com.minigoogle.crawlerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageData {
    private String url;
    private String title;
    private String content;
}