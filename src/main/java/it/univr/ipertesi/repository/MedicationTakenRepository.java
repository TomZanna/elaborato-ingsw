package it.univr.ipertesi.repository;

import it.univr.ipertesi.model.MedicationTaken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationTakenRepository extends JpaRepository<MedicationTaken, Integer> {
}
