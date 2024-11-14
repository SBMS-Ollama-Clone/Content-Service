package com.kkimleang.contentservice.controller;

import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.*;
import com.kkimleang.contentservice.dto.*;
import com.kkimleang.contentservice.model.*;
import com.kkimleang.contentservice.service.*;
import java.io.*;
import java.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    private final ElasticSearchService elasticSearchService;

    @GetMapping("/{chatId}/all")
    public Response<List<ContentResponse>> getAllContent(@PathVariable String chatId) {
        try {
            return Response.<List<ContentResponse>>ok()
                    .setPayload(contentService.getAllContentsByChatId(chatId));
        } catch (Exception e) {
            return Response.<List<ContentResponse>>notFound().setErrors(e.getMessage());
        }
    }

    @PostMapping
    public Response<ContentResponse> createContent(@RequestBody ContentRequest contentRequest) {
        return Response.<ContentResponse>created()
                .setPayload(contentService.createContent(contentRequest));
    }

    @PutMapping("/{contentId}/update")
    public Response<ContentResponse> updateContent(@PathVariable String contentId, @RequestBody ContentRequest contentRequest) {
        return Response.<ContentResponse>ok()
                .setPayload(contentService.updateContent(contentId, contentRequest));
    }

    @DeleteMapping("/{chatId}/delete/all")
    public Response<String> deleteAllContents(@PathVariable String chatId) {
        Integer deleted = contentService.deleteAllContentsByChatId(chatId);
        if (deleted > 0) {
            return Response.<String>ok().setPayload("Deleted " + deleted + " contents");
        } else {
            return Response.<String>notFound().setErrors("No content found");
        }
    }

    @GetMapping("/search")
    public Response<List<ContentResponse>> searchContents(@RequestParam String keyword) {
        try {
            SearchResponse<Content> contents = elasticSearchService.fuzzySearch(keyword);
            List<Hit<Content>> hitList = contents.hits().hits();
            List<ContentResponse> contentResponses = new ArrayList<>();
            for (Hit<Content> hit : hitList) {
                assert hit.source() != null;
                contentResponses.add(ContentResponse.fromContent(hit.source()));
            }
            return Response.<List<ContentResponse>>ok().setPayload(contentResponses);
        } catch (IOException e) {
            log.error("Error: {}", e.getMessage(), e);
            return Response.<List<ContentResponse>>notFound().setErrors(e.getMessage());
        }
    }
}
