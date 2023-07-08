package it.univr.ipertesi.utils;

import it.univr.ipertesi.model.Doctor;
import it.univr.ipertesi.model.Patient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserSession {
    private String fiscalCode;
    private boolean isPatient;

    public UserSession() {
        this.resetSession();
    }

    public void resetSession() {
        fiscalCode = "";
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFromPatient(Patient patient) { fiscalCode = patient.fiscalCode; }

    public void setFromDoctor(Doctor doctor) { fiscalCode = doctor.fiscalCode; }

}
