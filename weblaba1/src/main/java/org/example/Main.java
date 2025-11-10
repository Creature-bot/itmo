package org.example;

import com.fastcgi.FCGIInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dto.HitResult;
import org.example.http.RequestHandler;
import org.example.service.CheckService;
import org.example.service.ResponseWriter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        ObjectMapper o = new ObjectMapper().registerModule(new JavaTimeModule());
        o.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ResponseWriter responseWriter = new ResponseWriter(o);

        CheckService checkService = new CheckService();
        List<HitResult> history = new CopyOnWriteArrayList<>();

        RequestHandler handler = new RequestHandler(o, responseWriter, checkService, history);

        FCGIInterface fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            String method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");

            if (method == null) {
                handler.sendErrorResponse("Неизвестный метод.");
                continue;
            }

            if (method.equals("GET")) {
                handler.sendErrorResponse("Мы не обрабатываем метод GET.");
                continue;
            }

            if (method.equals("POST")) {
                handler.handlePostRequest();
                continue;
            }

            handler.sendErrorResponse("Метод не поддерживается: " + method);
        }
    }
}
