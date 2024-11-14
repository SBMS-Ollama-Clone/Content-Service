package com.kkimleang.contentservice.elastic;

import com.kkimleang.contentservice.model.*;
import java.util.*;
import org.springframework.data.elasticsearch.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface ContentElasticRepository extends ElasticsearchRepository<Content, String> {
    List<Content> findAllByChatIdOrderByCreatedAtAsc(String chatId);
}
