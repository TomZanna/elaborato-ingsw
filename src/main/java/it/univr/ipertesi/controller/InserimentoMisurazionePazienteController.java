package it.univr.ipertesi.controller;

import it.univr.ipertesi.model.PressureMeasurement;
import it.univr.ipertesi.model.Symptom;
import it.univr.ipertesi.repository.PressureMeasurementRepository;
import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
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
import java.util.EnumSet;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class InserimentoMisurazionePazienteController implements Initializable {
    private final UserSession userSession;
    private final PressureMeasurementRepository pressureMeasurementRepository;
    private final StageManager stageManager;
    @FXML
    private Button buttonLogout;
    @FXML
    private Button buttonConferma;
    @FXML
    private Spinner<Integer> countPressioneSistoica;
    @FXML
    private Spinner<Integer> countPressioneDistoica;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField textOra;
    @FXML
    private CheckBox checkNausea;
    @FXML
    private CheckBox checkFebbre;
    @FXML
    private CheckBox checkTesta;
    @FXML
    private CheckBox checkAltro;

    public InserimentoMisurazionePazienteController(UserSession userSession, PressureMeasurementRepository pressureMeasurementRepository, StageManager stageManager) {
        this.userSession = userSession;
        this.pressureMeasurementRepository = pressureMeasurementRepository;
        this.stageManager = stageManager;
    }

    public void backToHomeHandler() {
        stageManager.switchScene(FXMLView.HOME_PAGE_PATIENT);
    }

    public void inserisciMisurazione() {
        PressureMeasurement ps = new PressureMeasurement(); // creo una nuova misurazione
        // set pressioni
        ps.setDiastolicPressure(countPressioneDistoica.getValue());
        ps.setSistolicPressure(countPressioneSistoica.getValue());
        // set data e ora
        ps.setDateTime(LocalDateTime.of(
                datePicker.getValue(),
                LocalTime.parse(textOra.getText(), DateTimeFormatter.ofPattern("HH:mm")))
        );

        EnumSet<Symptom> symptoms = EnumSet.noneOf(Symptom.class);
        // check dei sintomi selezionati. Bruttina sta catena di if ma funzia
        if (checkTesta.isSelected()) {
            symptoms.add(Symptom.HEADACHE);
        }
        if (checkAltro.isSelected()) {
            symptoms.add(Symptom.OTHER);
        }
        if(checkFebbre.isSelected()) {
            symptoms.add(Symptom.FEVER);
        }
        if(checkNausea.isSelected()) {
            symptoms.add(Symptom.NAUSEA);
        }
        // set dei sintomi percepiti
        ps.setSymptoms(symptoms);

        ps.setPatient(userSession.getPatient()); // collego la misurazione al paziente attualmente loggato

        pressureMeasurementRepository.save(ps); // salvo nel database la misurazione

        stageManager.switchScene(FXMLView.HOME_PAGE_PATIENT);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        textOra.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));

        Pattern timeFieldPattern = Pattern.compile("^(?:[01]\\d|2[0-3]):[0-5]\\d$");

        Validator validator = new Validator();

        validator.createCheck().dependsOn("timeField", textOra.textProperty()).withMethod(c -> {
            String userInput = c.get("timeField");
            if (!timeFieldPattern.matcher(userInput).find()) {
                c.error("Ora non valida");
            } else if (LocalTime.parse(textOra.getText(), DateTimeFormatter.ofPattern("HH:mm")).isAfter(LocalTime.now())) {
                c.error("Non puoi inserire misurazioni future");
            }
        }).decorates(textOra).immediate();

        countPressioneDistoica.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
        validator.createCheck().dependsOn("pressioneD", countPressioneSistoica.valueProperty()).withMethod(context -> {
            int count = context.get("pressioneD");
            if (count <= 0) {
                context.error("Valore non valido");
            }
        }).decorates(countPressioneDistoica).immediate();

        countPressioneSistoica.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
        validator.createCheck().dependsOn("pressioneS", countPressioneSistoica.valueProperty()).withMethod(context -> {
            int count = context.get("pressioneS");
            if (count <= 0) {
                context.error("Valore non valido");
            }
        }).decorates(countPressioneDistoica).immediate();

        validator.createCheck().dependsOn("datePicker", datePicker.valueProperty()).withMethod(context -> {
            LocalDate date = context.get("datePicker");
            if (date == null) {
                context.error("Data non valida");
            } else if (date.isAfter(LocalDate.now())) {
                context.error("Non puoi inserire misurazioni future");
            }
        }).decorates(datePicker).immediate();

        // lego lo stato del bottone alla validità del testo inserito
        buttonConferma.disableProperty().bind(validator.containsErrorsProperty());
    }
}
