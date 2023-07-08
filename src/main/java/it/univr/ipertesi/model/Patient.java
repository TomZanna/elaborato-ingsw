package it.univr.ipertesi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patient extends DashboardUser {
    @ManyToOne
    public Doctor doctor;
    public String additionalInfo;
}
