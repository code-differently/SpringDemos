package com.codedifferently.springtddrestcontroller.domain.inventoryItem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
