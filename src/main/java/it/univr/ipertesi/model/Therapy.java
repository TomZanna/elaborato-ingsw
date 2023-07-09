package it.univr.ipertesi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Therapy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Patient patient; // paziente sottoposto alla terapia
    @OneToOne
    private Doctor doctor; // dottore che ha formulato la terapia
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "therapy_id")
    private List<Prescription> prescriptions; // farmaci che compongono la terapia
}
