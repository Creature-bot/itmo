package se.ifmo.lab2WEB.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.ifmo.lab2WEB.config.ObjectMapperProvider;
import se.ifmo.lab2WEB.dto.Response;
import se.ifmo.lab2WEB.service.AcceptableValueValidator;
import se.ifmo.lab2WEB.service.HitDetector;
import se.ifmo.lab2WEB.service.HitResultService;
import se.ifmo.lab2WEB.service.ResponseWriter;

import java.io.IOException;

@WebServlet(name = "controller", value = "/controller")
public class ControllerServlet extends HttpServlet {
    private final ObjectMapper mapper = ObjectMapperProvider.getObjectMapper();
    private final ResponseWriter responseWriter = new ResponseWriter(mapper);
    private final HitResultService hitResultService = new HitResultService(new HitDetector(), new AcceptableValueValidator());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("area_check");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        hitResultService.deleteAll(req.getServletContext());
        Response response = Response.builder()
                .message("Успешная очистка")
                .build();
        responseWriter.writeResp(resp, response, 200);
    }
}
