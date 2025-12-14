package se.ifmo.lab3web.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import se.ifmo.lab3web.dto.HitDTO;
import se.ifmo.lab3web.entity.Hit;
import se.ifmo.lab3web.repository.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Named("hitService")
@ApplicationScoped
@NoArgsConstructor
@Transactional
public class HitService {
    @Inject
    private Repository<Hit> hitRepository;
    @Inject
    private HitDetector hitDetector;

    public Hit createHit(HitDTO hitDto) {

        OffsetDateTime now = OffsetDateTime.now();
        long timeStart = System.nanoTime();
        boolean hitStatus = hitDetector.identifyHit(hitDto.getX(), hitDto.getY(), hitDto.getR());
        long timEnd = System.nanoTime();
        long timeExecution = timEnd - timeStart;
        Hit hitResult = Hit.builder()
                .x(hitDto.getX())
                .y(hitDto.getY())
                .r(hitDto.getR())
                .timeStart(now)
                .executionTime(timeExecution)
                .hitStatus(hitStatus)
                .build();
        return hitRepository.save(hitResult);
    }

    public int deleteAll() {
        return hitRepository.deleteAll();
    }

    public List<Hit> findAll() {
        return hitRepository.findAll();
    }
}
