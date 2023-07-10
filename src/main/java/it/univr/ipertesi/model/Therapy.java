package it.univr.ipertesi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "therapy_id")
    private List<Prescription> prescriptions; // farmaci che compongono la terapia

    private LocalDate startDate;

    public void copyFromTherapy(Therapy t) {
        this.setPatient(t.getPatient());
        this.setDoctor(t.getDoctor());
        this.setStartDate(t.getStartDate());
        this.setPrescriptions(t.getPrescriptions().stream().map(prescription -> {
            Prescription tmp = new Prescription();
            tmp.copyFrom(prescription);
            tmp.setTherapy(this);
            return tmp;
        }).collect(Collectors.toList()));
    }
}
