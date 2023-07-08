package it.univr.ipertesi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Prescriptions {
    @Id
    public int id;
    public String name;
    public int timesPerDay;
    public int suggestedQuantity;
    public String indications;
    @ManyToOne
    @JoinColumn(name = "therapy_id")
    public Therapy therapy;
}
