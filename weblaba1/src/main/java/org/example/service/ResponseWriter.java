package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

public class ResponseWriter {
    private final String ANSWER_200 = """
            HTTP/1.1 200 OK
            Content-Type: application/json; charset=utf-8
            Content-Length: %d
            
            %s
            """;
    private final String ANSWER_400 = """
            HTTP/1.1 400 Bad Request
            Content-Type: application/json; charset=utf-8
            Content-Length: %d
            
            %s
            """;

    private ObjectMapper o;

    public ResponseWriter(ObjectMapper o) {
        this.o = o;
    }

    public void writeGoodResponse(Object data) throws JsonProcessingException {
        String json = o.writeValueAsString(data);
        System.out.print(ANSWER_200.formatted(
                json.getBytes(StandardCharsets.UTF_8).length,
                json
        ));
    }

    public void writeBadResponse(Object data) throws JsonProcessingException {
        String json = o.writeValueAsString(data);
        System.out.print(ANSWER_400.formatted(
                json.getBytes(StandardCharsets.UTF_8).length,
                json
        ));
    }
}