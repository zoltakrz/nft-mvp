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

            Map<String, Object> token299 = new HashMap<>();
            token299.put("tokenID", 299);
            token299.put("firstName", "Joe");
            token299.put("lastName", "Fortestingmiddleman");
            token299.put("certType", "Architect");
            token299.put("certLevel", "1");
            expectedDataMap.put("token299", token299);

            Map<String, Object> token300 = new HashMap<>();
            token300.put("tokenID", 300);
            token300.put("firstName", "Joe");
            token300.put("lastName", "Fortestingmiddleman");
            token300.put("certType", "Architect");
            token300.put("certLevel", "0");
            expectedDataMap.put("token300", token300);
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