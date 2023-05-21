package it.questura.passaporti.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
public class Citizen {
    @Id
    private String fiscalCode;
    private Date passportReleaseDate;
    @Enumerated
    private PassportState state;

    private String name;
    private String surname;
    private Date birthDate;
    private String birthPlace;
    private String healthInsuranceCardNumber;
    private int categories;
}
