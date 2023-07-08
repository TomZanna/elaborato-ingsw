package it.univr.ipertesi.repository;

import it.univr.ipertesi.model.PressureMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PressureMeasurementRepository extends JpaRepository<PressureMeasurement, Integer> {
}
