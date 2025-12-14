package se.ifmo.lab3web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HitDTO {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
}