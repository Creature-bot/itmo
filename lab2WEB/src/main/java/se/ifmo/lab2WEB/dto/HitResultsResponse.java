package se.ifmo.lab2WEB.dto;

import lombok.*;
import se.ifmo.lab2WEB.entity.HitResult;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HitResultsResponse{
    private ArrayList<HitResult> hitResults;
}
