package it.univr.ipertesi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class MedicationTaken {
    @Id
    public int id;

    @OneToOne
    public Prescriptions prescription;

    public LocalDateTime dateTime;

    public int quantityTaken;
}
