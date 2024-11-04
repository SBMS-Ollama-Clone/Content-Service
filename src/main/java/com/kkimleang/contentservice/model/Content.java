package com.kkimleang.contentservice.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_contents")
public class Content {
    @Id
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
    @Column(name = "message", nullable = false, length = 2048)
    private String message;

    public enum MessageType {
        PROMPT, RESPONSE
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private MessageType messageType;

    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
