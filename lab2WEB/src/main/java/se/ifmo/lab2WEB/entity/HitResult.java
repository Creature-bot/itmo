package se.ifmo.lab2WEB.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import se.ifmo.lab2WEB.service.serializer.SmartBigDecimalSerializer;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HitResult {

    @JsonSerialize(using = SmartBigDecimalSerializer.class)
    private BigDecimal x;

    @JsonSerialize(using = SmartBigDecimalSerializer.class)
    private BigDecimal y;

    @JsonSerialize(using = SmartBigDecimalSerializer.class)
    private BigDecimal r;

    private boolean hitStatus;

    private long executionTime;

    private OffsetDateTime timeStart;

}
