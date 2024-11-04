package com.kkimleang.contentservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContentRequest {
    private String chatId;
    private String modelName;
    private String message;
    private String messageType;
}
