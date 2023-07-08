package it.univr.ipertesi.controller;

import it.univr.ipertesi.model.Citizen;
import it.univr.ipertesi.model.PassportState;
import it.univr.ipertesi.repository.CitizenRepository;
import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private final StageManager stageManager;
    private final CitizenRepository citizenRepository;
    private final UserSession userSession;

    private Alert notFoundPopup;
    @FXML
    private TextField fiscalCode;
    @FXML
    private Button loginButton;

    @Autowired
    public LoginController(CitizenRepository citizenRepository, StageManager stageManager, UserSession userSession) {
        this.citizenRepository = citizenRepository;
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
                    if (!userInput.equals("admin") && !fiscalCodePatter.matcher(userInput).find()) {
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

        // se è admin, vado alla pagina di inserimento
        if (userInput.equals("admin")) {
            stageManager.switchScene(FXMLView.INSERT_AVAILABILITY);
            return;
        }

        // cerco codice fiscale
        Optional<Citizen> queryOutput = citizenRepository.findById(userInput);

        // verifico se è presente nel database
        if (queryOutput.isPresent()) {
            Citizen citizen = queryOutput.get();
            userSession.setFromCitizen(citizen);
            stageManager.switchScene(FXMLView.HOME_PAGE);
            //stageManager.switchScene(FXMLView.CITIZEN_SERVICES);
        } else {
            notFoundPopup.showAndWait();
        }
    }
}
