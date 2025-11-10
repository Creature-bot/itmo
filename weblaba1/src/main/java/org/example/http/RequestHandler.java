package org.example.http;

import com.fastcgi.FCGIInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.HitResult;
import org.example.dto.Response;
import org.example.exception.ValidationException;
import org.example.service.CheckService;
import org.example.service.InputValidator;
import org.example.service.ResponseWriter;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RequestHandler {

    private final ObjectMapper o;
    private final ResponseWriter responseWriter;
    private final CheckService checkService;
    private final List<HitResult> history;

    public RequestHandler(ObjectMapper o,
                          ResponseWriter responseWriter,
                          CheckService checkService,
                          List<HitResult> history) {
        this.o = o;
        this.responseWriter = responseWriter;
        this.checkService = checkService;
        this.history = history;
    }

    public void handlePostRequest() {
        try {
            String requestBody = readRequestBody();
            Map<String, String> params = parseFormData(requestBody);

            int x = Integer.parseInt(params.get("x"));
            BigDecimal y = new BigDecimal(params.get("y"));
            float r = Float.parseFloat(params.get("r"));

            verifyY(y);

            InputValidator.ValidationResult validation = InputValidator.validate(x, y, r);
            if (!validation.isValid()) {
                throw new ValidationException(validation.getMessage());
            }

            HitResult hitResult = checkService.checkHit(x, y, r);
            history.add(hitResult);

            Response response = Response.builder()
                    .success(true)
                    .message("Точка проверена успешно")
                    .currentResult(hitResult)
                    .history(new ArrayList<>(history))
                    .build();

            responseWriter.writeGoodResponse(response);

        } catch (NumberFormatException e) {
            sendErrorResponse("Некорректный формат числа: " + e.getMessage());
        } catch (ValidationException e) {
            sendErrorResponse(e.getMessage());
        } catch (Exception e) {
            sendErrorResponse("Ошибка обработки запроса: " + e.getMessage());
        }
    }

    public void sendErrorResponse(String message) {
        try {
            Response r = Response.builder()
                    .success(false)
                    .message(message)
                    .build();
            responseWriter.writeBadResponse(r);
        } catch (Exception e) {
            System.err.println("Ошибка при отправке ошибки: " + e.getMessage());
        }
    }

    public void verifyY(BigDecimal y) {
        BigDecimal LOWER_BOUND = new BigDecimal("-3");
        BigDecimal UPPER_BOUND = new BigDecimal("5");
        if (!(y.compareTo(LOWER_BOUND) >= 0 && y.compareTo(UPPER_BOUND) <= 0)) {
            throw new ValidationException("y не является числом от -3 до 5");
        }
    }

    public String readRequestBody() throws IOException {
        FCGIInterface.request.inStream.fill();
        int contentLength = FCGIInterface.request.inStream.available();
        ByteBuffer buffer = ByteBuffer.allocate(contentLength);
        int readBytes = FCGIInterface.request.inStream.read(buffer.array(), 0, contentLength);

        byte[] requestBodyRaw = new byte[readBytes];
        buffer.get(requestBodyRaw);
        buffer.clear();

        return new String(requestBodyRaw, StandardCharsets.UTF_8);
    }

    public Map<String, String> parseFormData(String formData) {
        Map<String, String> params = new HashMap<>();
        if (formData == null || formData.isEmpty()) {
            return params;
        }

        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                String value = java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                params.put(keyValue[0], value);
            }
        }
        return params;
    }
}
