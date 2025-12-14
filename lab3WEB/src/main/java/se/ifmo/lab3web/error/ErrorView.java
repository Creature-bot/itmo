package se.ifmo.lab3web.error;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Setter;

import java.io.IOException;

@Named("errorView")
@ApplicationScoped
@Setter
public class ErrorView {
    private String message;

    public String getMessage() {
        if (message==null || message.isEmpty()){
            return "Internal error occurred";
        }
        return message;
    }

    public void showError(String message){
        setMessage(message);
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            System.out.println(message);
            externalContext.redirect(externalContext.getRequestContextPath()+"/error.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
