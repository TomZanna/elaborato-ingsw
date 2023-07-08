package it.univr.ipertesi.controller;

import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class HomePagePazienteController {

    @FXML
    private Button dataButton;
    @FXML
    private Button farmaciButton;
    @FXML
    private Button visualizzaButton;

    private StageManager stageManager;

    public HomePagePazienteController(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void handlerDati() {
            stageManager.switchScene(FXMLView.INSERIMENTO_DATI);
    }

    public void handlerFarmaci() {
            stageManager.switchScene(FXMLView.INSERIMENTO_DATI);
    }

    public void handlerVisualizza() {
            stageManager.switchScene(FXMLView.INSERIMENTO_DATI);
    }

}