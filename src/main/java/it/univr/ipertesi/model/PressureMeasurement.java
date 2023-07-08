package it.univr.ipertesi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"PATIENT_ID", "DATETIME"})
})
public class PressureMeasurement implements Comparable<PressureMeasurement> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID")
    public Patient patient; // paziente che ha effettuato la misurazione
    public LocalDateTime dateTime; // data e ora della misurazione
    @Transient
    public EnumSet<Synthom> symptoms; // sintomi percepiti dal paziente
    public String otherSynthoms; // espande il sintomo "altro"
    public int diastolicPressure;
    public int sistolicPressure;
    private int symptomsBitString;

    public int getSymptomsBitString() {
        int ret = 0;

        for (Synthom val : symptoms) {
            ret |= (1 << val.ordinal());
        }

        return ret;
    }

    public void setSymptomsBitString(int symptomsBitString) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PressureMeasurement that = (PressureMeasurement) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(PressureMeasurement other) {
        if (this.id == other.id)
            return 0;

        // ordinamento in base alla data e ora di misurazione
        return this.dateTime.compareTo(other.dateTime);
    }
}
