package it.univr.ipertesi.integration;

import it.univr.ipertesi.IpertesiApplication;
import it.univr.ipertesi.model.PressureMeasurement;
import it.univr.ipertesi.model.Symptom;
import it.univr.ipertesi.repository.PatientRepository;
import it.univr.ipertesi.repository.PressureMeasurementRepository;
import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import org.testfx.util.WaitForAsyncUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(ApplicationExtension.class)
@SpringBootTest(classes = IpertesiApplication.class)
public class InserimentoMisurazionePazienteControllerTest {
    @Autowired
    UserSession userSession;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ConfigurableApplicationContext springContext;
    @Autowired
    PressureMeasurementRepository pressureMeasurementRepository;
    StageManager stageManager;

    @Start
    void start(Stage stage) {
        stageManager = springContext.getBean(StageManager.class, stage);
        stageManager.switchScene(FXMLView.INSERIMENTO_MISURAZIONE);
        stage.show();
    }

    @BeforeEach
    public void initEach() {
        stageManager.switchScene(FXMLView.INSERIMENTO_MISURAZIONE);
        userSession.setPatient(patientRepository.findById("VRDLSE99A41F205P").get());
    }

    @Test
    void testInsertDefaultFields(FxRobot robot) {
        long oldCount = pressureMeasurementRepository.count();

        robot.clickOn("#buttonConferma");

        long newCount = pressureMeasurementRepository.count();
        FxAssert.verifyThat("#farmaciButton", Node::isVisible);
        Assertions.assertEquals(oldCount + 1, newCount);
    }

    @Test
    void insertWithNausea(FxRobot robot) {
        long oldCount = pressureMeasurementRepository.count();

        robot.clickOn("#checkNausea");
        robot.clickOn("#buttonConferma");

        long newCount = pressureMeasurementRepository.count();
        List<PressureMeasurement> pressureMeasurements = pressureMeasurementRepository.findAllByPatientAndDateTimeAfter(userSession.getPatient(), LocalDateTime.now().minusMinutes(2));

        FxAssert.verifyThat("#farmaciButton", Node::isVisible);
        Assertions.assertEquals(oldCount + 1, newCount);
        Assertions.assertTrue(pressureMeasurements.get(0).getSymptoms().contains(Symptom.NAUSEA));
    }

    @Test
    void testInsertDateInFuture(FxRobot robot) {
        long oldCount = pressureMeasurementRepository.count();
        LocalTime inTheFuture = LocalTime.now().plusMinutes(2);

        TextField timeField = robot.lookup("#textOra").queryAs(TextField.class);
        Platform.runLater(() -> timeField.setText(inTheFuture.format(DateTimeFormatter.ofPattern("HH:mm"))));
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#buttonConferma");

        long newCount = pressureMeasurementRepository.count();

        FxAssert.verifyThat("#buttonConferma", Node::isDisabled);
        Assertions.assertEquals(oldCount, newCount);
    }

}

