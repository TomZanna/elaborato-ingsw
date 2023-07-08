package it.univr.ipertesi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MedicationTaken { // rappresenta l'assunzione di un farmaco
    @Id
    private int id;

    @ManyToOne
    private Prescriptions prescription; // farmaco a cui e' legata l'assunzione

    private LocalDateTime dateTime; // data e ora in cui è stato assunto il farmaco

    private int quantityTaken; // quantità di farmaco assunta
}
