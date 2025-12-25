package com.minigoogle.searchservice.controller;

import com.minigoogle.searchservice.model.IndexedPage;
import com.minigoogle.searchservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * REST Controller for handling search requests.
 */
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Critical for Phase 3: Allows the frontend to call this API
public class SearchController {

    private final SearchService searchService;

    /**
     * Endpoint to search for pages using fuzzy matching and highlighting.
     * @param keyword The term to search for.
     * @return List of matching pages.
     */
    @GetMapping
    public List<IndexedPage> search(@RequestParam String keyword) throws IOException {
        return searchService.search(keyword);
    }
}