package it.questura.passaporti.controller;

import it.questura.passaporti.model.Citizen;
import it.questura.passaporti.repository.CitizenRepository;
import it.questura.passaporti.utils.FXMLView;
import it.questura.passaporti.utils.StageManager;
import it.questura.passaporti.utils.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
@AllArgsConstructor(access = AccessLevel.PRIVATE) // per D.I. durante testing
public class LoginController {
    private final StageManager stageManager;
    private final CitizenRepository citizenRepository;
    private final UserSession userSession;
    @FXML
    private TextField fiscalCode;

    @Autowired
    public LoginController(CitizenRepository citizenRepository, StageManager stageManager, UserSession userSession) {
        this.citizenRepository = citizenRepository;
        this.stageManager = stageManager;
        this.userSession = userSession;
    }

    public void clickHandler() {
        // cerco codice fiscale
        Optional<Citizen> c1 = citizenRepository.findById(fiscalCode.getText());
        // verifico se è presente nel database
        if (c1.isPresent()) {
            System.out.println("Cittadino trovato: " + c1);

            // passo alla schermata dei servizi
            stageManager.switchScene(FXMLView.CITIZEN_SERVICES);
        } else {
            System.out.println("User non torvato");
            // TODO: mostrare un alert/dialog
        }
    }

}
