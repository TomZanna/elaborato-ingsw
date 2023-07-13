package it.univr.ipertesi.controller;

import it.univr.ipertesi.model.Therapy;
import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class HomePagePazienteController {
    private final StageManager stageManager;
    private final UserSession userSession;

    @Autowired
    public HomePagePazienteController(StageManager stageManager, UserSession userSession) {
        this.stageManager = stageManager;
        this.userSession = userSession;
    }

    public void handlerInserimentoMisurazione() {
        stageManager.switchScene(FXMLView.INSERIMENTO_MISURAZIONE);
    }

    public void handlerInserimentoAssunzioneFarmaco() {
        Therapy therapy = userSession.getPatient().getTherapy();

        if (therapy == null || therapy.getPrescriptions().isEmpty()) {
            Alert notFoundPopup = new Alert(Alert.AlertType.INFORMATION);
            notFoundPopup.setHeaderText("Nessuna terapia!");
            notFoundPopup.setContentText("Puoi contattare il tuo medico scrivendo a " + userSession.getPatient().getDoctor().getEmail());
            notFoundPopup.showAndWait();
        } else {
            stageManager.switchScene(FXMLView.ASSUNZIONE_FARMACO);
        }
    }

    public void handlerLogout() {
        userSession.resetSession();
        stageManager.switchScene(FXMLView.LOGIN);
    }

}