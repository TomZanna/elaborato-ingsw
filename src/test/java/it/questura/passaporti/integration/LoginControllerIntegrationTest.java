package it.questura.passaporti.integration;

import it.questura.passaporti.PassaportiApplication;
import it.questura.passaporti.model.Citizen;
import it.questura.passaporti.repository.CitizenRepository;
import it.questura.passaporti.utils.FXMLView;
import it.questura.passaporti.utils.StageManager;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@Transactional // Ripristina il database dopo ogni test
@ExtendWith(ApplicationExtension.class)
@SpringBootTest(classes = PassaportiApplication.class)
class LoginControllerIntegrationTest {
    @Autowired
    CitizenRepository citizenRepository;
    @Autowired
    ConfigurableApplicationContext springContext;

    @Start
    void start(Stage stage) {
        StageManager stageManager = springContext.getBean(StageManager.class, stage);
        stageManager.switchScene(FXMLView.LOGIN);
        stage.show();
    }

    @Test
    void testCitizenNotPresentInRegistry(FxRobot robot) {
        String fiscalCode = "RSSMRA80L05F593A";

        // digito il suo codice fiscale
        robot.clickOn("#fiscalCode").write(fiscalCode);
        // premo su login
        robot.clickOn("#loginButton");

        // TODO: controllare che venga mostrato un messaggio di errore
        Assertions.assertThat("#loginButton").isVisible();
    }

    @Test
    void testCitizenEnabledForRequest(FxRobot robot) {
        String fiscalCode = "RSSMRA80L05F593A";
        // inserisco un cittadino nel database
        Citizen citizen = new Citizen();
        citizen.setFiscalCode(fiscalCode);
        citizenRepository.save(citizen);

        // digito il suo codice fiscale
        robot.clickOn("#fiscalCode").write(fiscalCode);
        // premo su login
        robot.clickOn("#loginButton");

        // verifico se è stato effettuato il cambio schermata
        Assertions.assertThat("#issueButton").isVisible();
    }
}
