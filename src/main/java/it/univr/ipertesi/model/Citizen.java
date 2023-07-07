package it.univr.ipertesi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Citizen {
    @Id
    private String fiscalCode;
    @Enumerated
    private PassportState state;
}
