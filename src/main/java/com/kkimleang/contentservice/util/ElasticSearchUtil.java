package com.kkimleang.contentservice.util;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import java.util.function.*;

public class ElasticSearchUtil {
    private final static String INDEX = "message";

    public static Supplier<Query> createSupplierQuery(String approximateProductName) {
        return () -> Query.of(q -> q.fuzzy(createFuzzyQuery(approximateProductName)));
    }


    public static FuzzyQuery createFuzzyQuery(String approximateProductName) {
        FuzzyQuery.Builder fuzzyQuery = new FuzzyQuery.Builder();
        return fuzzyQuery.field(INDEX).value(approximateProductName).build();

    }
}