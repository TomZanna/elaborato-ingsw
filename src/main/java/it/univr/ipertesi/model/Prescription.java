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
    private int id;
    @ManyToOne
    @JoinColumn(name = "therapy_id")
    private Therapy therapy; // terapia a cui è associato il farmaco

    private String name; // nome del farmaco prescritto
    private int timesPerDay; // numero di assunzioni giornaliere
    private int suggestedQuantity; // quantità per assunzione
    private String indications; // ulteriori indicazioni/istruzioni

    @Override
    public String toString() {
        return String.format("%s: n. %d, %d al giorno, %s", name, suggestedQuantity, timesPerDay, indications);
    }
}
