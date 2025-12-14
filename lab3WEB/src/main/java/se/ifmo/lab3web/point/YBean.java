package se.ifmo.lab3web.point;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Named("yBean")
@SessionScoped
public class YBean implements Serializable {
    private BigDecimal y;

    public void validateYBeanValue(FacesContext context, UIComponent component, Object value){
        try {
            BigDecimal yValue = new BigDecimal(value.toString());
            verifyY(yValue);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage("y не является числом от -5 до 5");
            throw new ValidatorException(message);
        }
    }

    private void verifyY(BigDecimal y) {
        BigDecimal LOWER_BOUND = new BigDecimal("-5");
        BigDecimal UPPER_BOUND = new BigDecimal("5");

        if (!(y.compareTo(LOWER_BOUND) >= 0 && y.compareTo(UPPER_BOUND) <= 0)) {
            throw new RuntimeException();
        }
    }

}