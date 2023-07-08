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
    // contenitore temporaneo per memorizzare paziente e/o dottore
    private Patient patient;
    private Doctor doctor;
    private boolean isPatient; // se l'utente loggato e' paziente (o dottore)

    public UserSession() {
        this.resetSession();
    }

    public void resetSession() {
        patient = null;
        doctor = null;
    }

    public void setFromPatient(Patient patient) {
        this.patient = patient;
        this.doctor = patient.getDoctor();
    }

    public void setFromDoctor(Doctor doctor) {
        this.doctor = doctor;
        patient = null;
    }
}
