package com.kkimleang.contentservice.service;

import co.elastic.clients.elasticsearch.*;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.*;
import com.kkimleang.contentservice.model.*;
import com.kkimleang.contentservice.util.*;
import java.io.*;
import java.util.function.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticSearchService {
    private final ElasticsearchClient elasticsearchClient;

    public SearchResponse<Content> fuzzySearch(String approximateMessage) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.createSupplierQuery(approximateMessage);
        SearchResponse<Content> response = elasticsearchClient
                .search(s -> s.index("contents").query(supplier.get()), Content.class);
        log.info("Supplier query: {}", supplier.get().toString());
        log.info("Fuzzy search response: {}", response);
        log.info("Fuzzy search response: {}", response.hits().hits());
        return response;
    }
}
