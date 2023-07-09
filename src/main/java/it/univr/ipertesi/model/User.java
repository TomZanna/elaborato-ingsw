package it.univr.ipertesi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "DashUser")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class User {
    @Id
    private String fiscalCode;
    private String name;
    private String surname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(fiscalCode, user.fiscalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fiscalCode);
    }
}
