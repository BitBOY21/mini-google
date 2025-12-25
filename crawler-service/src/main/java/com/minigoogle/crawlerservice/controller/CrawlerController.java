package com.minigoogle.crawlerservice.controller;

import com.minigoogle.crawlerservice.model.CrawlRequest;
import com.minigoogle.crawlerservice.service.CrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/crawler")
@RequiredArgsConstructor
public class CrawlerController {

    private final CrawlerService crawlerService;

    @PostMapping("/crawl")
    public String crawlPage(@RequestBody CrawlRequest request) {
        try {
            return crawlerService.crawlAndSend(request.getUrl());
        } catch (IOException e) {
            return "Failed to crawl page: " + e.getMessage();
        }
    }
}