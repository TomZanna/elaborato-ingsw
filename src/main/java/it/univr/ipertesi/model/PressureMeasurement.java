package it.univr.ipertesi.model;

import jakarta.persistence.*;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Entity
@Getter
@Setter
public class PressureMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Patient patient; // paziente che ha effettuato la misurazione
    private LocalDateTime dateTime; // data e ora della misurazione

    private int diastolicPressure;
    private int sistolicPressure;
    // sintomi percepiti dal paziente
    // NON TOCCARE, manipolato da getter/setter di EnumSet<Symptom>
    private int symptomsBitString;

    public Color getColor() {
        if (diastolicPressure < 90) {
            if(sistolicPressure < 140) {
                return Color.LIMEGREEN;
            } else if (sistolicPressure < 150) {
                return Color.YELLOW;
            } else {
                return Color.ORANGE;
            }
        } else if (sistolicPressure < 160) {
            return Color.ORANGE;
        } else if (sistolicPressure < 180) {
            return Color.DARKORANGE;
        } else {
            return Color.INDIANRED;
        }
    }

    public EnumSet<Symptom> getSymptoms() {
        EnumSet<Symptom> result = EnumSet.noneOf(Symptom.class);

        for (Symptom val : Symptom.values()) {
            if (((1 << val.getId()) & symptomsBitString) != 0) {
                result.add(val);
            }
        }

        return result;
    }

    public void setSymptoms(EnumSet<Symptom> symptoms) {
        symptomsBitString = 0;
        for (Symptom val : symptoms) {
            symptomsBitString |= (1 << val.getId());
        }
    }
}
