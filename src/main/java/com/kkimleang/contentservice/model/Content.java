package com.kkimleang.contentservice.model;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
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
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @Column(name = "message", nullable = false, length = 10000)
    private String message;

    public enum MessageType {
        PROMPT, RESPONSE
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private MessageType messageType;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Field(type = FieldType.Date)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Field(type = FieldType.Date)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
