package it.univr.ipertesi.controller;

import it.univr.ipertesi.model.Doctor;
import it.univr.ipertesi.model.Patient;
import it.univr.ipertesi.model.PressureMeasurement;
import it.univr.ipertesi.model.Symptom;
import it.univr.ipertesi.repository.PatientRepository;
import it.univr.ipertesi.repository.PressureMeasurementRepository;
import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class HomePageMedicoController implements Initializable {
    private final StageManager stageManager;
    private final UserSession userSession;
    private final PressureMeasurementRepository pressureMeasurementRepository;
    private final PatientRepository patientRepository;
    @FXML
    private ChoiceBox<Patient> patientsChoiceBox;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField fiscalCodeField;
    @FXML
    private TextField mainDoctorField;
    @FXML
    private TextField additionalInfoField;
    @FXML
    private TextField recentSymptomsField;
    @FXML
    private TextField meanSistolicField;
    @FXML
    private TextField meanDiastolicField;
    @FXML
    private TextField minSistolicField;
    @FXML
    private TextField minDiastolicField;
    @FXML
    private TextField maxSistolicField;
    @FXML
    private TextField maxDiastolicField;

    @Autowired
    public HomePageMedicoController(StageManager stageManager, UserSession userSession, PressureMeasurementRepository pressureMeasurementRepository, PatientRepository patientRepository) {
        this.stageManager = stageManager;
        this.userSession = userSession;
        this.pressureMeasurementRepository = pressureMeasurementRepository;
        this.patientRepository = patientRepository;
    }

    public void logoutHandler() {
        userSession.resetSession();
        stageManager.switchScene(FXMLView.LOGIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Patient> patientList = patientRepository.findAll();

        patientsChoiceBox.setItems(FXCollections.observableList(patientList));

        patientsChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            nameField.setText(newValue.getName());
            surnameField.setText(newValue.getSurname());
            additionalInfoField.setText(newValue.getAdditionalInfo());
            fiscalCodeField.setText(newValue.getFiscalCode());

            List<PressureMeasurement> measurements = pressureMeasurementRepository
                    .findAllByPatientAndDateTimeAfter(newValue, LocalDateTime.now().minusWeeks(1));

            String tmp = measurements.stream()
                    .map(PressureMeasurement::getSymptoms)
                    .flatMap(Collection::stream)
                    .distinct()
                    .map(Symptom::getTitle)
                    .collect(Collectors.joining(", "));

            recentSymptomsField.setText(tmp.isBlank() ? "nessuno": tmp);

            IntSummaryStatistics diasolicPressStats = measurements.stream().mapToInt(PressureMeasurement::getDiastolicPressure).summaryStatistics();
            meanDiastolicField.setText(String.valueOf(diasolicPressStats.getAverage()));
            minDiastolicField.setText(String.valueOf(diasolicPressStats.getMin()));
            maxDiastolicField.setText(String.valueOf(diasolicPressStats.getMax()));

            IntSummaryStatistics sistolicPressStats = measurements.stream().mapToInt(PressureMeasurement::getSistolicPressure).summaryStatistics();
            meanSistolicField.setText(String.valueOf(sistolicPressStats.getAverage()));
            minSistolicField.setText(String.valueOf(sistolicPressStats.getMin()));
            maxSistolicField.setText(String.valueOf(sistolicPressStats.getMax()));

            Doctor newDoc = newValue.getDoctor();
            mainDoctorField.setText(newDoc + (newDoc.equals(userSession.getDoctor()) ? " (tu)" : ""));
        });

        patientsChoiceBox.getSelectionModel().select(0);

    }
}
