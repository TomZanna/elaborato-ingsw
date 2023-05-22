package it.questura.passaporti.repository;

import it.questura.passaporti.model.Citizen;
import it.questura.passaporti.model.CitizenAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<CitizenAlert, Integer> {
    // trova tutti gli alert del cittadino che vengono dopo la data specificata
    List<CitizenAlert> findAllByCitizenAndDateAfter(Citizen citizen, LocalDate date);

    // trova gli alert del cittadino dopo la data specificata che hanno riscontro nella tabella delle disponibilità
    @Query(value = "select AL.* from CITIZEN_ALERT AL inner join AVAILABILITY AV " +
            "on AL.OFFICE = AV.OFFICE and AL.DATE = AV.DATE AND AL.SERVICE = AV.SERVICE " +
            "where AV.FREE_SLOTS > 0 and AL.CITIZEN = :citizen and AL.DATE > :date", nativeQuery = true)
    List<CitizenAlert> findEffectiveAlerts(Citizen citizen, LocalDate date);
}
