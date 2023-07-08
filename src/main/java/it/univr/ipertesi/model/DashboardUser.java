package it.univr.ipertesi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract public class DashboardUser {
    @Id
    public String fiscalCode;
    public String name;
    public String surname;
}
