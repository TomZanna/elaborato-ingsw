package it.univr.ipertesi.repository;

import it.univr.ipertesi.model.Patient;
import it.univr.ipertesi.model.PressureMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PressureMeasurementRepository extends JpaRepository<PressureMeasurement, Integer> {
    List<PressureMeasurement> findAllByPatientAndDateTimeAfter(Patient patient, LocalDateTime dateTime);
}
