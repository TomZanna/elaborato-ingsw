package it.univr.ipertesi.controller;

import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class homePageController implements Initializable {

    private StageManager stageManager;

    public homePageController(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        stageManager.switchScene(FXMLView.HOME_PAGE);

    }

}
