package it.questura.passaporti.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class Citizen {
    @Id
    private String fiscalCode;
    private LocalDate passportReleaseDate;
    @Enumerated
    private PassportState state;

    private String name;
    private String surname;
    private LocalDate birthDate;
    private String birthPlace;
    private String healthInsuranceCardNumber;
    private int categories;
}
