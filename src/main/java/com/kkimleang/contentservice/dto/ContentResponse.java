package com.kkimleang.contentservice.dto;

import com.kkimleang.contentservice.model.Content;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class ContentResponse {
    private String contentId;
    private String chatId;
    private String modelName;
    private String message;
    private String messageType;
    private String createdAt;
    private String updatedAt;

    public static List<ContentResponse> fromContents(List<Content> contents) {
        return contents.stream().map(ContentResponse::fromContent).collect(Collectors.toList());
    }

    public static ContentResponse fromContent(Content content) {
        ContentResponse contentResponse = new ContentResponse();
        contentResponse.setContentId(content.getId());
        contentResponse.setChatId(content.getChatId());
        contentResponse.setModelName(content.getModelName());
        contentResponse.setMessage(content.getMessage());
        contentResponse.setMessageType(String.valueOf(content.getMessageType()));
//        contentResponse.setCreatedAt(content.getCreatedAt().toString());
//        contentResponse.setUpdatedAt(content.getUpdatedAt().toString());
        return contentResponse;
    }
}
