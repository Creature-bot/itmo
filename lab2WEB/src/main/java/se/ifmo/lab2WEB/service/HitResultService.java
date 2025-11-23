package se.ifmo.lab2WEB.service;

import jakarta.servlet.ServletContext;
import se.ifmo.lab2WEB.dto.HitResultDto;
import se.ifmo.lab2WEB.entity.HitResult;
import se.ifmo.lab2WEB.entity.ResultStorage;

import java.time.OffsetDateTime;

public class HitResultService {
    private final HitDetector hitDetector;
    private final AcceptableValueValidator acceptableValueValidator;

    public HitResultService(HitDetector hitDetector, AcceptableValueValidator acceptableValueValidator) {
        this.hitDetector = hitDetector;
        this.acceptableValueValidator = acceptableValueValidator;
    }

    public HitResult createHitResult(ServletContext context, HitResultDto hitResultDto, OffsetDateTime now) {
        acceptableValueValidator.verifyData(hitResultDto);
        boolean hitStatus = hitDetector.identifyHit(hitResultDto.getX(), hitResultDto.getY(), hitResultDto.getR());
        HitResult hitResult = HitResult.builder()
                .x(hitResultDto.getX())
                .y(hitResultDto.getY())
                .r(hitResultDto.getR())
                .timeStart(now)
                .hitStatus(hitStatus)
                .build();
        ResultStorage resultStorage = (ResultStorage) context.getAttribute("list");
        if (resultStorage == null) {
            resultStorage = new ResultStorage();
            context.setAttribute("list", resultStorage);
        }
        resultStorage.addHitResult(hitResult);
        return hitResult;
    }

    public void deleteAll(ServletContext context) {
        if (context.getAttribute("list") != null) {
            ResultStorage resultStorage = ((ResultStorage) context.getAttribute("list"));
            resultStorage.clear();
        }
    }
}
