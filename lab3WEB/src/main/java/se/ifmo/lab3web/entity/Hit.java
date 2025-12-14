package se.ifmo.lab3web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hit")
public class Hit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal x;

    private BigDecimal y;

    private BigDecimal r;

    private boolean hitStatus;

    private long executionTime;

    private OffsetDateTime timeStart;
}
