package com.minigoogle.searchservice.service;

import com.minigoogle.searchservice.model.IndexedPage;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchClient elasticsearchClient;

    /**
     * Performs a fuzzy search with highlighting across indexed pages.
     * * @param keyword The search term provided by the user.
     * @return A list of matching pages with highlighted snippets.
     * @throws IOException If communication with Elasticsearch fails.
     */
    public List<IndexedPage> search(String keyword) throws IOException {
        // Execute the search against the "pages" index
        SearchResponse<IndexedPage> response = elasticsearchClient.search(s -> s
                        .index("web_pages")
                        .query(q -> q
                                .multiMatch(m -> m
                                        .fields("title", "content")
                                        .query(keyword)
                                        // Enables tolerance for typos (e.g., "Jvaa" matches "Java")
                                        .fuzziness("AUTO")
                                )
                        )
                        .highlight(h -> h
                                .fields("content", f -> f)
                                // Wraps the matched terms in bold HTML tags for display
                                .preTags("<b>")
                                .postTags("</b>")
                        ),
                IndexedPage.class
        );

        List<IndexedPage> results = new ArrayList<>();

        // Process each search hit and extract highlights
        for (Hit<IndexedPage> hit : response.hits().hits()) {
            IndexedPage page = hit.source();
            if (page != null) {
                // Map the highlight fragments to the model
                Map<String, List<String>> highlightMap = hit.highlight();
                if (highlightMap != null && highlightMap.containsKey("content")) {
                    page.setHighlights(highlightMap.get("content"));
                }
                results.add(page);
            }
        }

        return results;
    }
}