package com.kkimleang.contentservice.repository;

import com.kkimleang.contentservice.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, String> {
    @Query("SELECT c FROM Content c WHERE c.chatId = ?1 ORDER BY c.createdAt ASC")
    List<Content> findAllByChatId(String chatId);

    @Modifying
    @Query("DELETE FROM Content c WHERE c.chatId = ?1")
    Integer deleteAllByChatId(String chatId);
}
