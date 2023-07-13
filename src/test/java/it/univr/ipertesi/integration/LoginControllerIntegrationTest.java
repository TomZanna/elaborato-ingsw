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
    void testPatientNotRegistered(FxRobot robot) {
        String fiscalCode = "ASSMRA80L05F593A";

        // digito il suo codice fiscale
        robot.clickOn("#fiscalCode").write(fiscalCode);
        robot.clickOn("#pazienteToggle");
        // premo su login
        robot.clickOn("#loginButton");

        FxAssert.verifyThat("#loginButton", Node::isVisible);
        robot.interact(() -> ((Stage) robot.lookup(".error").query().getScene().getWindow()).close());
    }

    @Test
    void testPatientRegistered(FxRobot robot) {
        String fiscalCode = "VRDLSE99A41F205P";

        // digito il suo codice fiscale
        robot.clickOn("#fiscalCode").write(fiscalCode);
        robot.clickOn("#pazienteToggle");
        // premo su login
        robot.clickOn("#loginButton");

        // verifico se è stato effettuato il cambio schermata
        FxAssert.verifyThat("#farmaciButton", Node::isVisible);
    }

    @Test
    void testDoctorNotRegistered(FxRobot robot) {
        String fiscalCode = "ASSMRA80L05F593A";

        // digito il suo codice fiscale
        robot.clickOn("#fiscalCode").write(fiscalCode);
        robot.clickOn("#medicoToggle");
        // premo su login
        robot.clickOn("#loginButton");

        FxAssert.verifyThat("#loginButton", Node::isVisible);
        robot.interact(() -> ((Stage) robot.lookup(".error").query().getScene().getWindow()).close());
    }

    @Test
    void testDoctorRegistered(FxRobot robot) {
        String fiscalCode = "BNCRNI85R63A433X";

        // digito il suo codice fiscale
        robot.clickOn("#fiscalCode").write(fiscalCode);
        robot.clickOn("#medicoToggle");
        // premo su login
        robot.clickOn("#loginButton");

        // verifico se è stato effettuato il cambio schermata
        FxAssert.verifyThat("#patientsChoiceBox", Node::isVisible);
    }

    @Test
    void testFiscalCodeNotValid(FxRobot robot) {
        String fiscalCode = "ciao";

        // digito il suo codice fiscale
        robot.clickOn("#fiscalCode").write(fiscalCode);
        robot.clickOn("#medicoToggle");
        // premo su login
        robot.clickOn("#loginButton");

        // verifico se è stato effettuato il cambio schermata
        FxAssert.verifyThat("#loginButton", Node::isDisabled);
    }
}
