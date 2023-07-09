package it.univr.ipertesi.controller;

import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class ModificaTerapiaController {

    private final StageManager stageManager;
    @FXML
    private Button cancelButton;

    public ModificaTerapiaController(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void back() {
        stageManager.switchScene(FXMLView.HOME_PAGE_DOCTOR);
    }

}
