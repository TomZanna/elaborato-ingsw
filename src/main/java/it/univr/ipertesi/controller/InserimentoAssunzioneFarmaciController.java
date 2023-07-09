package it.univr.ipertesi.controller;

import it.univr.ipertesi.model.MedicationTaken;
import it.univr.ipertesi.model.Prescription;
import it.univr.ipertesi.repository.MedicationTakenRepository;
import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.synedra.validatorfx.Validator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class InserimentoAssunzioneFarmaciController implements Initializable {
    private final MedicationTakenRepository medicationTakenRepository;

    private final UserSession userSession;
    private final StageManager stageManager;

    @FXML
    private ChoiceBox<Prescription> medicationSelector;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeField;
    @FXML
    private Spinner<Integer> countField;
    @FXML
    private Button insertButton;

    public InserimentoAssunzioneFarmaciController(MedicationTakenRepository medicationTakenRepository, UserSession userSession, StageManager stageManager) {
        this.medicationTakenRepository = medicationTakenRepository;
        this.userSession = userSession;
        this.stageManager = stageManager;
    }


    public void insertMedication() {
        MedicationTaken medicationTaken = new MedicationTaken();

        Prescription p = medicationSelector.getSelectionModel().getSelectedItem();
        medicationTaken.setPrescription(p);

        countField.commitValue();
        medicationTaken.setQuantityTaken(countField.getValue());

        medicationTaken.setDateTime(LocalDateTime.of(
                datePicker.getValue(),
                LocalTime.parse(timeField.getText(), DateTimeFormatter.ofPattern("HH:mm")))
        );

        medicationTakenRepository.save(medicationTaken);
        stageManager.switchScene(FXMLView.HOME_PAGE_PATIENT);
    }

    public void backToHome() {
        stageManager.switchScene(FXMLView.HOME_PAGE_PATIENT);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));

        List<Prescription> fetchedList = userSession.getPatient().getTherapy().getPrescriptions();

        medicationSelector.setItems(FXCollections.observableList(fetchedList));
        medicationSelector.getSelectionModel().select(0);

        datePicker.setValue(LocalDate.now());
        timeField.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));

        Pattern timeFieldPattern = Pattern.compile("^(?:[01]\\d|2[0-3]):[0-5]\\d$");

        Validator validator = new Validator();

        validator.createCheck().dependsOn("timeField", timeField.textProperty()).withMethod(c -> {
            String userInput = c.get("timeField");
            if (!timeFieldPattern.matcher(userInput).find()) {
                c.error("Ora non valida");
            }
        }).decorates(timeField).immediate();

        validator.createCheck().dependsOn("countField", countField.valueProperty()).withMethod(context -> {
            int count = context.get("countField");
            if (count <= 0) {
                context.error("Quantità non valida");
            }
        }).decorates(countField).immediate();

        validator.createCheck().dependsOn("selectedMedication", medicationSelector.itemsProperty()).withMethod(context -> {
            ObservableList list = context.get("selectedMedication");
            if (list.isEmpty()) {
                context.error("Nessun farmaco selezionato");
            }
        }).decorates(medicationSelector).immediate();

        validator.createCheck().dependsOn("datePicker", datePicker.valueProperty()).withMethod(context -> {
            LocalDate date = context.get("datePicker");
            if (date == null) {
                context.error("Data non valida");
            }
        }).decorates(datePicker).immediate();

        // lego lo stato del bottone alla validità del testo inserito
        insertButton.disableProperty().bind(validator.containsErrorsProperty());
    }
}
