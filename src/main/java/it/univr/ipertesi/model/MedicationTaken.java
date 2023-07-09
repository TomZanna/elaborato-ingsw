package it.univr.ipertesi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MedicationTaken { // rappresenta l'assunzione di un farmaco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Prescription prescription; // farmaco a cui e' legata l'assunzione

    private LocalDateTime dateTime; // data e ora in cui è stato assunto il farmaco

    private int quantityTaken; // quantità di farmaco assunta
}
