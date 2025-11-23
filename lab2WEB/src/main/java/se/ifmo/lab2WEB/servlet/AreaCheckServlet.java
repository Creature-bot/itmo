package se.ifmo.lab2WEB.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.ifmo.lab2WEB.config.ObjectMapperProvider;
import se.ifmo.lab2WEB.dto.HitResultDto;
import se.ifmo.lab2WEB.dto.Response;
import se.ifmo.lab2WEB.entity.HitResult;
import se.ifmo.lab2WEB.exception.InvalidDataException;
import se.ifmo.lab2WEB.service.*;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@WebServlet(name = "area_check", value = "/area_check")
public class AreaCheckServlet extends HttpServlet {
    private final ObjectMapper mapper = ObjectMapperProvider.getObjectMapper();
    private final ResponseWriter responseWriter =  new ResponseWriter(mapper);
    private final HitResultService hitResultService = new HitResultService(new HitDetector(), new AcceptableValueValidator());
    private final RequestValidator requestValidator = new RequestValidator();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        long timeStart = System.nanoTime();
        try {
            HitResultDto hitResultDto = requestValidator.getHitResult(req);
            ServletContext servletContext = req.getServletContext();
            HitResult hitResult = hitResultService.createHitResult(servletContext, hitResultDto, now);
            hitResult.setExecutionTime(System.nanoTime() - timeStart);
            resp.sendRedirect(req.getContextPath() + "/result.jsp");
        } catch (InvalidDataException e) {
            Response response = Response.builder()
                    .message(e.getMessage())
                    .build();
            responseWriter.writeResp(resp, response, 400);
        }
    }
}
