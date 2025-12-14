package se.ifmo.lab3web.repository;

import java.util.List;

public interface Repository<T> {
    T save(T entity);

    int deleteAll();

    List<T> findAll();
}
