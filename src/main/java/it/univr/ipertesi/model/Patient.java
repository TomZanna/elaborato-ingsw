package it.univr.ipertesi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Patient extends DashboardUser {
    @ManyToOne
    public Doctor doctor;
    public String additionalInfo;
}
