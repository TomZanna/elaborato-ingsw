package it.questura.passaporti.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"citizen", "office", "service", "date"}))
public class CitizenAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    @JoinColumn(name = "citizen")
    public Citizen citizen;
    @ManyToOne
    @JoinColumn(name = "office")
    public Office office;
    @Enumerated
    public ServiceType service;
    public LocalDate date;
}
