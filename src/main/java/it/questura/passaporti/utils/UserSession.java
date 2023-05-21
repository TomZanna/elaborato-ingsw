package it.questura.passaporti.utils;

import it.questura.passaporti.model.Citizen;
import it.questura.passaporti.model.PassportState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@Getter
@Setter
public class UserSession {
    private String fiscalCode;
    private PassportState passportState;
    private Date releaseDate;

    public UserSession() {
        this.resetSession();
    }

    public void resetSession() {
        fiscalCode = "";
        passportState = PassportState.NOT_REGISTERED;
        releaseDate = Date.from(Instant.ofEpochSecond(0));
    }

    public void setFromCitizen(Citizen citizen) {
        fiscalCode = citizen.getFiscalCode();
        passportState = citizen.getState();
        releaseDate = citizen.getPassportReleaseDate();
    }

}
