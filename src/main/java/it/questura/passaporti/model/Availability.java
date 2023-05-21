package it.questura.passaporti.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"office", "service", "date", "slot_index"}))
public class Availability {
    @ManyToOne
    @JoinColumn(name = "office")
    public Office office;
    @Enumerated(EnumType.ORDINAL)
    public ServiceType service;
    public Date date;
    @Column(name = "slot_index")
    public int slotIndex;
    public int freeSlots = 0;
    public int reservedSlots = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    public Time getStartTime() {
        // TODO: calcolare ora inizio come office.openingTime + (slotIndex * 15 minuti)
        return null;
    }
}
