package com.kkimleang.contentservice.service;

import com.kkimleang.contentservice.dto.*;
import com.kkimleang.contentservice.elastic.*;
import com.kkimleang.contentservice.exception.*;
import com.kkimleang.contentservice.model.*;
import com.kkimleang.contentservice.repository.*;
import jakarta.transaction.*;
import java.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;
    private final ContentElasticRepository contentElasticRepository;

    @Override
    public ContentResponse createContent(ContentRequest content) {
        System.out.println(content);
        Content newContent = new Content();
        newContent.setChatId(content.getChatId());
        newContent.setModelName(content.getModelName());
        newContent.setMessage(content.getMessage());
        newContent.setMessageType(Content.MessageType.valueOf(content.getMessageType()));
        newContent = contentRepository.save(newContent);
        contentElasticRepository.save(newContent);
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

    @Transactional
    @Override
    public List<ContentResponse> getAllContentsByChatId(String chatId) {
        try {
            try {
                List<Content> contents = contentElasticRepository.findAllByChatIdOrderByCreatedAtAsc(chatId);
                log.info(contents.toString());
                if (contents.isEmpty()) {
                    throw new Exception("No contents found in elastic search");
                }
                return ContentResponse.fromContents(contents);
            } catch (Exception e) {
                log.error("Error: {}", e.getMessage());
                List<Content> dbContents = contentRepository.findAllByChatId(chatId);
                if (dbContents.isEmpty()) {
                    return new ArrayList<>();
                }
                return ContentResponse.fromContents(dbContents);
            }
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
            throw new ResourceNotFoundException("No contents of chat id: " + chatId + " found");
        }
    }

    @Transactional
    @Override
    public Integer deleteAllContentsByChatId(String chatId) {
        return contentRepository.deleteAllByChatId(chatId);
    }
}
