package it.univr.ipertesi.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Therapy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @ManyToOne
    public Patient patient; // paziente sottoposto alla terapia
    @OneToOne
    public Doctor doctor; // dottore che ha formulato la terapia
    @OneToMany()
    @JoinColumn(name = "therapy_id")
    public Set<Prescriptions> prescriptions; // farmaci che compongono la terapia
}
