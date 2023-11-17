package com.capgemini.middleware.infrastructure.adapters.input.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpectedTestData {

    private static Map<String, Map<String, Object>> expectedDataMap;

    private static void initializeData() {
        if (expectedDataMap == null) {
            expectedDataMap = new HashMap<>();

            Map<String, Object> token291 = new HashMap<>();
            token291.put("tokenIndex", 291);
            token291.put("firstName", "Joe");
            token291.put("lastName", "Fortestingmiddleman");
            token291.put("certType", "Architect");
            token291.put("certLevel", "1");
            expectedDataMap.put("token291", token291);

            Map<String, Object> token292 = new HashMap<>();
            token292.put("tokenIndex", 292);
            token292.put("firstName", "Joe");
            token292.put("lastName", "Fortestingmiddleman");
            token292.put("certType", "Architect");
            token292.put("certLevel", "0");
            expectedDataMap.put("token292", token292);
        }
    }

    public static Map<String, Object> getData(String name) {
        initializeData();
        return expectedDataMap.get(name);
    }

    public static List<Map<String, Object>> getAllData() {
        initializeData();
        return new ArrayList<>(expectedDataMap.values());
    }
}