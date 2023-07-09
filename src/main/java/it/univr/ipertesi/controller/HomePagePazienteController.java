package it.univr.ipertesi.controller;

import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
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
        stageManager.switchScene(FXMLView.ASSUNZIONE_FARMACO);
    }

    public void handlerLogout() {
        userSession.resetSession();
        stageManager.switchScene(FXMLView.LOGIN);
    }

}