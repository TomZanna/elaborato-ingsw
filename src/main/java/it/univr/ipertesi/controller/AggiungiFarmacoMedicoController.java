package it.univr.ipertesi.controller;

import it.univr.ipertesi.model.Prescription;
import it.univr.ipertesi.model.Therapy;
import it.univr.ipertesi.repository.PatientRepository;
import it.univr.ipertesi.repository.TherapyRepository;
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
import java.util.List;
import java.util.ResourceBundle;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class AggiungiFarmacoMedicoController implements Initializable {

    private final StageManager stageManager;
    private final UserSession userSession;
    private final TherapyRepository therapyRepository;
    private final PatientRepository patientRepository;
    @FXML
    private Button confirmButton;
    @FXML
    private Spinner<Integer> spinnerDoseDaAssumere;
    @FXML
    private Spinner<Integer> spinnerQuantitaDaAssumere;
    @FXML
    private TextField medicineName;
    @FXML
    private TextArea textAreaUlterioriInformazioni;


    public AggiungiFarmacoMedicoController(StageManager stageManager, UserSession userSession, TherapyRepository therapyRepository, PatientRepository patientRepository) {
        this.stageManager = stageManager;
        this.userSession = userSession;
        this.therapyRepository = therapyRepository;
        this.patientRepository = patientRepository;
    }

    public void back() {
        stageManager.switchScene(FXMLView.MODIFICA_TERAPIA);
    }

    public void confirm() {

        Therapy terapia = userSession.getPatient().getTherapy();

        Prescription p = new Prescription();

        p.setName(medicineName.getText());

        p.setIndications(textAreaUlterioriInformazioni.getText());

        p.setTimesPerDay(spinnerQuantitaDaAssumere.getValue());

        p.setSuggestedQuantity(spinnerDoseDaAssumere.getValue());

        p.setTherapy(terapia);
        terapia.getPrescriptions().add(p);

        back();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        spinnerDoseDaAssumere.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        spinnerQuantitaDaAssumere.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,Integer.MAX_VALUE,1));

        Validator validator = new Validator();
        validator.createCheck().dependsOn("medicineName", medicineName.textProperty()).withMethod(c -> {
            String userInput = c.get("medicineName");

            if (medicineName.getText().isEmpty()) {
                c.error("errore");
            }

        }).decorates(medicineName).immediate();
        // lego lo stato del bottone alla validità del testo inserito
        confirmButton.disableProperty().bind(validator.containsErrorsProperty());



    }
}
