package it.univr.ipertesi.controller;

import it.univr.ipertesi.model.Doctor;
import it.univr.ipertesi.model.Patient;
import it.univr.ipertesi.repository.DoctorRepository;
import it.univr.ipertesi.repository.PatientRepository;
import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.synedra.validatorfx.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
@AllArgsConstructor(access = AccessLevel.PRIVATE) // per D.I. durante testing
public class LoginController implements Initializable {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final StageManager stageManager;
    private final UserSession userSession;

    private Alert notFoundPopup;
    @FXML
    private TextField fiscalCode;
    @FXML
    private Button loginButton;
    @FXML
    private Toggle pazienteToggle;

    @Autowired
    public LoginController(PatientRepository patientRepository, DoctorRepository doctorRepository, StageManager stageManager, UserSession userSession) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.stageManager = stageManager;
        this.userSession = userSession;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // regex per i codice fiscali di persone
        Pattern fiscalCodePatter = Pattern.compile("^([A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST][0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{3}[A-Z])$");

        Validator validator = new Validator();
        validator.createCheck()
                .dependsOn("fiscalCode", fiscalCode.textProperty())
                .withMethod(c -> {
                    String userInput = c.get("fiscalCode");
                    if (!fiscalCodePatter.matcher(userInput).find()) {
                        c.error("Codice fiscale non valido");
                    }
                }).decorates(fiscalCode)
                .immediate();
        // lego lo stato del bottone alla validità del testo inserito
        loginButton.disableProperty().bind(validator.containsErrorsProperty());

        notFoundPopup = new Alert(Alert.AlertType.ERROR);
        notFoundPopup.setHeaderText("Utente non trovato!");
        notFoundPopup.setContentText("In caso di problemi inviare una segnalazione a ipertesi@univr.it");
    }

    public void clickHandler() {
        String userInput = fiscalCode.getText();

        // cerco codice fiscale nel paziente
        Optional<Patient> queryOutput = patientRepository.findById(userInput);

        // cerco codice fiscale nel dottore
        Optional<Doctor> queryOutput2 = doctorRepository.findById(userInput);

        // seleziono il paziente
        if (pazienteToggle.isSelected()) {
            // eseguo l'interrogazione

            if (queryOutput.isPresent()) {

                Patient patient = queryOutput.get();
                userSession.setFromPatient(patient);
                stageManager.switchScene(FXMLView.HOME_PAGE);   // da cambiare con HOME_PAGE_PATIENT

            }
            else {
                notFoundPopup.showAndWait();
            }

        } else if (!pazienteToggle.isSelected()) {

            if (queryOutput2.isPresent()) {

                Doctor doctor = queryOutput2.get();
                userSession.setFromDoctor(doctor);
                stageManager.switchScene(FXMLView.HOME_PAGE_MEDICO);

            }
            else {
                notFoundPopup.showAndWait();
            }
        }
    }
}
