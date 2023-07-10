package it.univr.ipertesi.repository;

import it.univr.ipertesi.model.Patient;
import it.univr.ipertesi.model.Therapy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    @Query(value = """
            SELECT DATE_ONLY, COUNT(*) AS DAY_COUNT
            FROM (select PRESCRIPTION_ID, SUM(QUANTITY_TAKEN) AS Q_TOT, FORMATDATETIME(DATE_TIME, 'yyyy-MM-dd') as DATE_ONLY
                  from MEDICATION_TAKEN MT
                  where DATE_TIME > DATEADD('DAY', -7, CURRENT_DATE)
                  GROUP BY DATE_ONLY, PRESCRIPTION_ID) C
                     JOIN PRESCRIPTION PS ON PS.ID = C.PRESCRIPTION_ID
            WHERE PS.SUGGESTED_QUANTITY * PS.TIMES_PER_DAY = C.Q_TOT
              AND THERAPY_ID = :therapy
            GROUP BY C.DATE_ONLY
            HAVING DAY_COUNT = :count""", nativeQuery = true)
    List<LocalDate> getAlert(Therapy therapy, int count);

    @Query(value = """
            select COUNT(*)
            from PRESCRIPTION PS
                     join MEDICATION_TAKEN MT on PS.ID = MT.PRESCRIPTION_ID
            where THERAPY_ID = :therapy
              and DATE_TIME > dateadd('day', -7, formatdatetime(DATE_TIME, 'yyyy-MM-dd'));""", nativeQuery = true)
    int getCount(int therapy);
}
