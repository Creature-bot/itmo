package se.ifmo.lab3web.point;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Named("rBean")
@SessionScoped
public class RBean implements Serializable {
    private BigDecimal r;

    public void validateRBeanValue(FacesContext context, UIComponent component, Object value) {
        try {
            BigDecimal rValue = new BigDecimal(value.toString());
            verifyR(rValue);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage("Значение r должно быть 1, 1.5, 2, 2.5 или 3.");
            throw new ValidatorException(message);
        }
    }

    private void verifyR(BigDecimal r) {
        final List<String> VALID_R_STRINGS = Arrays.asList("1", "1.5", "2", "2.5", "3");

        final List<BigDecimal> VALID_R = VALID_R_STRINGS.stream()
                .map(s -> new BigDecimal(s).setScale(10, RoundingMode.HALF_UP))
                .toList();

        BigDecimal scaledR = r.setScale(10, RoundingMode.HALF_UP);

        if (!VALID_R.contains(scaledR)) {
            throw new RuntimeException();
        }
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }

    public void setR(String r) {
        this.r = BigDecimal.valueOf(Long.parseLong(r));
    }



}
