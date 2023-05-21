package it.questura.passaporti.controller;

import it.questura.passaporti.model.Availability;
import it.questura.passaporti.model.Office;
import it.questura.passaporti.model.ServiceType;
import it.questura.passaporti.repository.AvailabilityRepository;
import it.questura.passaporti.repository.OfficeRepository;
import it.questura.passaporti.utils.StageManager;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class AdminController implements Initializable {
    private final StageManager stageManager;
    private final AvailabilityRepository availabilityRepository;
    private final OfficeRepository officeRepository;

    @Autowired
    public AdminController(StageManager stageManager, AvailabilityRepository availabilityRepository, OfficeRepository officeRepository) {
        this.stageManager = stageManager;
        this.availabilityRepository = availabilityRepository;
        this.officeRepository = officeRepository;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Office office = officeRepository.findById(1).orElseThrow();

        Availability availability = new Availability();
        availability.date = new Date(2023, 6, 10);
        availability.service = ServiceType.WITHDRAW;
        availability.office = office;
        availability.slotIndex = 5;
        availability.freeSlots = office.numberOfEmployee;

        availabilityRepository.save(availability);
    }
}
