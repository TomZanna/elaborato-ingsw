package it.univr.ipertesi.controller;

import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class HomePagePazienteController {
    private final StageManager stageManager;

    @Autowired
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