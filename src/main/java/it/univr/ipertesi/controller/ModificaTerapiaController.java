package it.univr.ipertesi.controller;

import it.univr.ipertesi.model.Prescription;
import it.univr.ipertesi.model.Therapy;
import it.univr.ipertesi.repository.PatientRepository;
import it.univr.ipertesi.repository.TherapyRepository;
import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class ModificaTerapiaController implements Initializable {

    private final StageManager stageManager;
    private final UserSession userSession;
    private final TherapyRepository therapyRepository;
    private final PatientRepository patientRepository;
    @FXML
    private Button cancelButton;
    @FXML
    private TableView<Prescription> tableViewFarmaci;
    @FXML
    private Button addMedicineButton;

    public ModificaTerapiaController(StageManager stageManager, UserSession userSession, TherapyRepository therapyRepository, PatientRepository patientRepository) {
        this.stageManager = stageManager;
        this.userSession = userSession;
        this.therapyRepository = therapyRepository;
        this.patientRepository = patientRepository;
    }

    public void back() {
        stageManager.switchScene(FXMLView.HOME_PAGE_DOCTOR);
    }

    public void removeSelectedMedicine() {
        Prescription p = tableViewFarmaci.getSelectionModel().getSelectedItem();

        userSession.getPatient().getTherapy().getPrescriptions().remove(p);

        populateTable();
    }

    private void populateTable() {
        tableViewFarmaci.setItems(FXCollections.observableList(userSession.getPatient().getTherapy().getPrescriptions()));
    }

    public void saveTherapy() {
        Therapy t = new Therapy();
        t.copyFromTherapy(userSession.getPatient().getTherapy());
        t.setStartDate(LocalDate.now());

        userSession.getPatient().setTherapy(therapyRepository.save(t));
        patientRepository.save(userSession.getPatient());

        back();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Prescription, String> medicationName = new TableColumn<>("Nome");
        medicationName.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getName()));
        tableViewFarmaci.getColumns().add(medicationName);

        TableColumn<Prescription, Integer> medicationQuatity = new TableColumn<>("Quantità");
        medicationQuatity.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTimesPerDay()));
        tableViewFarmaci.getColumns().add(medicationQuatity);

        TableColumn<Prescription, Integer> medicationPerDay = new TableColumn<>("Dose per assunzione");
        medicationPerDay.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSuggestedQuantity()));
        tableViewFarmaci.getColumns().add(medicationPerDay);

        TableColumn<Prescription, String> medicationIndication = new TableColumn<>("Indicazioni");
        medicationIndication.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getIndications()));
        tableViewFarmaci.getColumns().add(medicationIndication);

        populateTable();
    }

    public void setScene() {
        stageManager.switchScene(FXMLView.AGGIUNGI_FARMACO_MEDICO);
    }

}
