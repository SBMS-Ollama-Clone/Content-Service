package com.kkimleang.contentservice.model;


import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "contents")
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_contents")
public class Content {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    public Content() {
        this.id = UUID.randomUUID().toString();
    }

    @Column(name = "chat_id", nullable = false)
    private String chatId;
    @Column(name = "model_name", nullable = false)
    private String modelName;
    @Lob
    @Column(name = "message", nullable = false)
    private String message;

    public enum MessageType {
        PROMPT, RESPONSE
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private MessageType messageType;

    @Field(type = FieldType.Date)
    @Column(name = "created_at")
    private Instant createdAt;
    @Field(type = FieldType.Date)
    @Column(name = "updated_at")
    private Instant updatedAt;
    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
