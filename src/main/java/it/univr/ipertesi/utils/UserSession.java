package it.univr.ipertesi.utils;

import it.univr.ipertesi.model.Citizen;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserSession {
    private String fiscalCode;

    public UserSession() {
        this.resetSession();
    }

    public void resetSession() {
        fiscalCode = "";
    }

    public void setFromCitizen(Citizen citizen) {
        fiscalCode = citizen.getFiscalCode();
    }

}
