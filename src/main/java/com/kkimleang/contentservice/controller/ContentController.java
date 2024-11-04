package com.kkimleang.contentservice.controller;

import com.kkimleang.contentservice.dto.ContentRequest;
import com.kkimleang.contentservice.dto.ContentResponse;
import com.kkimleang.contentservice.dto.Response;
import com.kkimleang.contentservice.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @GetMapping("/{chatId}/all")
    public Response<List<ContentResponse>> getAllContent(@PathVariable String chatId) {
        return Response.<List<ContentResponse>>ok()
                .setPayload(contentService.getAllContentsByChatId(chatId));
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
}
