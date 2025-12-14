package se.ifmo.lab3web.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import se.ifmo.lab3web.entity.Hit;

import java.util.List;


@Named("hitRepository")
@ApplicationScoped
public class HitRepository implements Repository<Hit> {

    public static final String SAVE_ERROR_MESSAGE = "Ошибка при сохранении сущности Hit.";
    public static final String DELETE_ALL_ERROR_MESSAGE = "Ошибка при массовом удалении записей";

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public Hit save(Hit entity) {
        try {
            em.persist(entity);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(SAVE_ERROR_MESSAGE, e);
        }
    }

    @Override
    public int deleteAll() {
        try {
            String jpql = "DELETE FROM Hit h";
            Query query = em.createQuery(jpql);
            int value = query.executeUpdate();
            return value;
        } catch (Exception e) {
            throw new RuntimeException(DELETE_ALL_ERROR_MESSAGE, e);
        }
    }

    public List<Hit> findAll() {
        String jpql = "SELECT h FROM Hit h ORDER BY h.id DESC";
        return em.createQuery(jpql, Hit.class)
                .getResultList();
    }
}