package it.questura.passaporti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Citizen {

    @Id
    @Column
    private String fiscalCode;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private int birthYear;

    @Column
    private String birthPlace;

    @Column
    private String healthInsuranceCardNumber;

    @Column
    private String finalRelease;

    @Override
    public String toString() {
        return "Citizen{" +
                "fiscalCode='" + fiscalCode + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthYear=" + birthYear +
                ", birthPlace='" + birthPlace + '\'' +
                ", healthInsuranceCardNumber='" + healthInsuranceCardNumber + '\'' +
                ", finalRelease='" + finalRelease + '\'' +
                '}';
    }
}
