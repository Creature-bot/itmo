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
@Named("xBean")
@SessionScoped
public class XBean implements Serializable {
    private BigDecimal x = null;

    public void validateXBeanValue(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            FacesMessage message = new FacesMessage("X должен быть выбран или кликнут на графике");
            throw new ValidatorException(message);
        }

        try {
            BigDecimal xValue = new BigDecimal(value.toString());
        } catch (NumberFormatException e) {
            FacesMessage message = new FacesMessage("X должен быть числом");
            throw new ValidatorException(message);
        }
    }
}
