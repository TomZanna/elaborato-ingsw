package it.univr.ipertesi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "therapy_id")
    private Therapy therapy; // terapia a cui è associato il farmaco

    private String name; // nome del farmaco prescritto
    private int timesPerDay; // numero di assunzioni giornaliere
    private int suggestedQuantity; // quantità per assunzione
    private String indications; // ulteriori indicazioni/istruzioni

    public void copyFrom(Prescription p) {
        this.therapy = p.therapy;
        this.name = p.name;
        this.timesPerDay = p.timesPerDay;
        this.suggestedQuantity = p.suggestedQuantity;
        this.indications = p.indications;
    }

    @Override
    public String toString() {
        return String.format("%s: n. %d, %d al giorno, %s", name, suggestedQuantity, timesPerDay, indications);
    }
}
