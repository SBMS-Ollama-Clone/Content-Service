package com.kkimleang.contentservice.service;

import com.kkimleang.contentservice.dto.ContentRequest;
import com.kkimleang.contentservice.dto.ContentResponse;

import java.util.List;

public interface ContentService {
    ContentResponse createContent(ContentRequest content);
    ContentResponse getContentById(String contentId);
    ContentResponse updateContent(String contentId, ContentRequest content);
    Boolean deleteContent(String contentId);
    List<ContentResponse> getAllContentsByChatId(String chatId);
    Integer deleteAllContentsByChatId(String chatId);
}
