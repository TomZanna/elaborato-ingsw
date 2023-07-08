package it.univr.ipertesi.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Doctor extends DashboardUser {
    public String email;
}
