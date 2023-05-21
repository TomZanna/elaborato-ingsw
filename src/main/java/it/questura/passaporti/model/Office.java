package it.questura.passaporti.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.sql.Time;


@Entity
public class Office {
    public String officeName;
    public String address;
    public int numberOfEmployee;
    public int numberOfTimeSlots;
    public Time openingTime;
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}