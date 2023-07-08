package it.univr.ipertesi.integration;

import it.univr.ipertesi.IpertesiApplication;
import it.univr.ipertesi.model.Patient;
import it.univr.ipertesi.repository.PatientRepository;
import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(ApplicationExtension.class)
@SpringBootTest(classes = IpertesiApplication.class)
class LoginControllerIntegrationTest {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ConfigurableApplicationContext springContext;

    @Start
    void start(Stage stage) {
        StageManager stageManager = springContext.getBean(StageManager.class, stage);
        stageManager.switchScene(FXMLView.LOGIN);
        stage.show();
    }

    @Test
    void testCitizenNotPresent(FxRobot robot) {
        String fiscalCode = "RSSMRA80L05F593A";

        // digito il suo codice fiscale
        robot.clickOn("#fiscalCode").write(fiscalCode);
        // premo su login
        robot.clickOn("#loginButton");

        // TODO: controllare che venga mostrato un messaggio di errore
        FxAssert.verifyThat("#loginButton", Node::isVisible);
        robot.interact(() -> ((Stage) robot.lookup(".error").query().getScene().getWindow()).close());
    }

    @Test
    void testCitizenRegistered(FxRobot robot) {
        String fiscalCode = "RSSMRA80L05F593A";
        // inserisco un cittadino nel database
        Patient patient = new Patient();
        patient.setFiscalCode(fiscalCode);
        patientRepository.save(patient);

        // digito il suo codice fiscale
        robot.clickOn("#fiscalCode").write(fiscalCode);
        // premo su login
        robot.clickOn("#loginButton");

        // verifico se è stato effettuato il cambio schermata
        FxAssert.verifyThat("#issueButton", Node::isVisible);
    }
}
