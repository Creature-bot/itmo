package se.ifmo.lab2WEB.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseWriter {
    private final ObjectMapper mapper;

    public ResponseWriter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void writeResp(HttpServletResponse response, Object obj, int statusCode) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setStatus(statusCode);
        System.out.println(mapper.writeValueAsString(obj));
        writer.write(mapper.writeValueAsString(obj));
    }
}
