package it.univr.ipertesi.model;

import jakarta.persistence.Entity;

@Entity
public class Doctor extends DashboardUser {
    public String email;
}
