package it.questura.passaporti.repository;

import it.questura.passaporti.model.Availability;
import it.questura.passaporti.model.Office;
import it.questura.passaporti.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
    // trova tutte le disponibilità dell'ufficio per il servizio richiesto e nell'intervallo specificato
    List<Availability> findAllByOfficeAndServiceAndDateBetween(Office office, ServiceType service, Date being, Date end);
}
