package se.ifmo.lab2WEB.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class HitResultDto {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonSetter(nulls = Nulls.FAIL)
    private BigDecimal x;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonSetter(nulls = Nulls.FAIL)
    private BigDecimal y;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonSetter(nulls = Nulls.FAIL)
    private BigDecimal r;

    @JsonCreator
    public HitResultDto(
            @JsonProperty(value = "x", required = true) BigDecimal x,
            @JsonProperty(value = "y", required = true) BigDecimal y,
            @JsonProperty(value = "r", required = true) BigDecimal r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
}
