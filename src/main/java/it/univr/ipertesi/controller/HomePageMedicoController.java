package it.univr.ipertesi.controller;

import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class HomePageMedicoController {

    @FXML
    private Button buttonLogout2;

    private final StageManager stageManager;

    public HomePageMedicoController(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void logoutHandler() {
        stageManager.switchScene(FXMLView.LOGIN);
    }

}
