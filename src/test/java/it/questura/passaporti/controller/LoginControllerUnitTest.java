package it.questura.passaporti.controller;

import it.questura.passaporti.RunWithFX;
import it.questura.passaporti.model.Citizen;
import it.questura.passaporti.repository.CitizenRepository;
import it.questura.passaporti.utils.FXMLView;
import it.questura.passaporti.utils.StageManager;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(RunWithFX.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class LoginControllerUnitTest {
    @Mock
    TextField fiscalCodeInputText;
    @Mock
    CitizenRepository repository;
    @Mock
    StageManager stageManager;
    @InjectMocks
    LoginController controller;

    @Test
    void userNotPresent() {
        String fiscalCode = "RSSMRA80L05F593A";

        when(fiscalCodeInputText.getText()).thenReturn(fiscalCode);
        when(repository.findById(fiscalCode)).thenReturn(Optional.empty());

        controller.clickHandler();

        verifyNoMoreInteractions(repository, stageManager);
    }

    @Test
    void userPresent() {
        String fiscalCode = "RSSMRA80L05F593A";
        // creo il cittadino da restituire
        Citizen citizen = new Citizen();
        citizen.setFiscalCode(fiscalCode);

        when(fiscalCodeInputText.getText()).thenReturn(fiscalCode);
        when(repository.findById(fiscalCode)).thenReturn(Optional.of(citizen));

        controller.clickHandler();

        verify(stageManager).switchScene(FXMLView.CITIZEN_SERVICES);
        verifyNoMoreInteractions(repository, stageManager);
    }
}