package it.univr.ipertesi.controller;

import it.univr.ipertesi.model.PressureMeasurement;
import it.univr.ipertesi.model.Symptom;
import it.univr.ipertesi.repository.PressureMeasurementRepository;
import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class InserimentoDatiPazienteController {
    private final UserSession userSession;
    private final PressureMeasurementRepository pressureMeasurementRepository;
    private final StageManager stageManager;
    @FXML
    private Button buttonLogout;
    @FXML
    private Button buttonConferma;
    @FXML
    private TextField textPressioneSistoica;
    @FXML
    private TextField textPressioneDistoica;
    @FXML
    private DatePicker datepiker;
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
    @FXML
    private TextField textAltro;

    public InserimentoDatiPazienteController(UserSession userSession, PressureMeasurementRepository pressureMeasurementRepository, StageManager stageManager) {
        this.userSession = userSession;
        this.pressureMeasurementRepository = pressureMeasurementRepository;
        this.stageManager = stageManager;
    }

    public void logoutHandler() {
        stageManager.switchScene(FXMLView.LOGIN);
    }

    /*
    public void initialize(URL location, ResourceBundle resources) {

       net.synedra.validatorfx.Validator validator = new Validator();
       validator.createCheck()
               .dependsOn("true", checkFebbre.textProperty())
               .withMethod(c -> {
                   checkFebbre.isSelected();
               });

       buttonConferma.disableProperty().bind(validator.containsErrorsProperty());

    }
     */

    public void controlCheck() {
        if (checkNausea.isSelected() || checkTesta.isSelected() || checkFebbre.isSelected() || checkAltro.isSelected()) {
            buttonConferma.setVisible(true);
        }
    }

    public void accettaHandler() {
        stageManager.switchScene(FXMLView.LOGIN);
    }

    public void inserisciMisurazione() {
        PressureMeasurement ps = new PressureMeasurement(); // creo una nuova misurazione
        // set pressioni
        ps.setDiastolicPressure(180);
        ps.setSistolicPressure(90);
        // set data e ora
        ps.setDateTime(LocalDateTime.of(
                datepiker.getValue(),
                LocalTime.of(10, 15) // prendere dal campo ora
        ));

        EnumSet<Symptom> symptoms = EnumSet.noneOf(Symptom.class);
        // aggiungere controllo altri sintomi ...
        if (checkTesta.isSelected()) {
            symptoms.add(Symptom.HEADACHE);
        }
        // set dei sintomi percepiti
        ps.setSymptoms(symptoms);

        ps.setPatient(userSession.getPatient()); // collego la misurazione al paziente attualmente loggato

        pressureMeasurementRepository.save(ps); // salvo nel database la misurazione
    }

}
