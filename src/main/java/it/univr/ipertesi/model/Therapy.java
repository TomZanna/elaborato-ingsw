package it.univr.ipertesi.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Therapy {
    @Id
    public int id;
    @ManyToOne
    public Patient patient;
    @OneToOne
    public Doctor doctor;
    @OneToMany()
    @JoinColumn(name = "therapy_id")
    public Set<Prescriptions> prescriptions;
}
