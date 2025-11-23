package se.ifmo.lab2WEB.entity;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ResultStorage {
    private final ArrayList<HitResult> results;

    public ResultStorage() {
        results = new ArrayList<>();
    }

    public void clear() {
        results.clear();
    }

    public void addHitResult(HitResult hitResult) {
        results.add(hitResult);
    }
}
