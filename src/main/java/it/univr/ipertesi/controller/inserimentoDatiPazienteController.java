package it.univr.ipertesi.controller;

import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.experimental.FieldNameConstants;
import net.synedra.validatorfx.Validator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class inserimentoDatiPazienteController {

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

    private final StageManager stageManager;

    public inserimentoDatiPazienteController(StageManager stageManager) {
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

}
