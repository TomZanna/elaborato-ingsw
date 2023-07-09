package it.univr.ipertesi.model;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class Doctor extends User {
    public String email; // email associata al medico

    @Override
    public String toString() {
        return getSurname() + " " + getName();
    }

}
