package com.kkimleang.contentservice.controller;

import com.kkimleang.contentservice.dto.*;
import com.kkimleang.contentservice.service.*;
import java.util.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

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
}
