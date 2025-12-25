package com.minigoogle.crawlerservice.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value; // חשוב מאוד!
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CrawlerService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ingestion.service.url}")
    private String ingestionServiceUrl;

    public String crawlAndSend(String url) throws IOException {
        // 1. Crawl the page
        Document doc = Jsoup.connect(url).get();
        String title = doc.title();
        String content = doc.body().text();

        // 2. Prepare data for Ingestion
        Map<String, String> data = new HashMap<>();
        data.put("url", url);
        data.put("title", title);
        data.put("content", content);

        // 3. Send to Ingestion Service (using the variable from @Value)
        try {
            restTemplate.postForEntity(ingestionServiceUrl, data, String.class);
            return "Successfully crawled and sent: " + title;
        } catch (Exception e) {
            return "Crawl succeeded but failed to send to Ingestion: " + e.getMessage();
        }
    }
}