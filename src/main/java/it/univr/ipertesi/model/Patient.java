package it.univr.ipertesi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patient extends User {
    @ManyToOne
    public Doctor doctor; // dottore di riferimento del paziente
    public String additionalInfo; // informazioni sul paziente compilate dal medico
    @OneToOne
    public Therapy therapy; // terapia che il paziente deve seguire
}
