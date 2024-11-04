package com.kkimleang.contentservice.service;

import com.kkimleang.contentservice.dto.ContentRequest;
import com.kkimleang.contentservice.dto.ContentResponse;
import com.kkimleang.contentservice.exception.ResourceNotFoundException;
import com.kkimleang.contentservice.model.Content;
import com.kkimleang.contentservice.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;

    @Override
    public ContentResponse createContent(ContentRequest content) {
        System.out.println(content);
        Content newContent = new Content();
        newContent.setChatId(content.getChatId());
        newContent.setModelName(content.getModelName());
        newContent.setMessage(content.getMessage());
        newContent.setMessageType(Content.MessageType.valueOf(content.getMessageType()));
        newContent = contentRepository.save(newContent);
        return ContentResponse.fromContent(newContent);
    }

    @Override
    public ContentResponse getContentById(String contentId) {
        Content content = contentRepository.findById(contentId).
                orElseThrow(() -> new ResourceNotFoundException("Content not found"));
        return ContentResponse.fromContent(content);
    }

    @Override
    public ContentResponse updateContent(String contentId, ContentRequest content) {
        Content updateContent = contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content not found"));
        contentRepository.findAllByChatId(updateContent.getChatId()).stream()
                .filter(c -> c.getCreatedAt().isAfter(updateContent.getCreatedAt()))
                .forEach(contentRepository::delete);
        return ContentResponse.fromContent(updateContent);
    }

    @Override
    public Boolean deleteContent(String contentId) {
        Content content = contentRepository.findById(contentId).
                orElseThrow(() -> new ResourceNotFoundException("Content not found"));
        contentRepository.delete(content);
        return true;
    }

    @Override
    public List<ContentResponse> getAllContentsByChatId(String chatId) {
        return ContentResponse.fromContents(contentRepository.findAllByChatId(chatId));
    }

    @Override
    public Boolean deleteContentsByChatId(String chatId) {
        return contentRepository.deleteAllByChatId(chatId);
    }
}
