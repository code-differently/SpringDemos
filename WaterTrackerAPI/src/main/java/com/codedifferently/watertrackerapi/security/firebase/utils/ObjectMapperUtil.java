package com.codedifferently.watertrackerapi.security.firebase.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperUtil {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
