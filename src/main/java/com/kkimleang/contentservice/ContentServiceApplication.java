package com.kkimleang.contentservice;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.elasticsearch.repository.config.*;
import org.springframework.data.jpa.repository.config.*;

@EnableElasticsearchRepositories(basePackages = "com.kkimleang.contentservice.elastic")
@EnableJpaRepositories(basePackages = "com.kkimleang.contentservice.repository")
@SpringBootApplication
public class ContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }

}
