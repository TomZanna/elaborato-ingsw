package it.univr.ipertesi.controller;

import it.univr.ipertesi.model.PressureMeasurement;
import it.univr.ipertesi.repository.PressureMeasurementRepository;
import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import it.univr.ipertesi.utils.UserSession;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Scope("prototype") // per evitare il riutilizzo del controller
public class VisualizzazioneStoricoPressioniController implements Initializable {
    private final UserSession userSession;
    private final PressureMeasurementRepository pressureMeasurementRepository;
    private final StageManager stageManager;

    @FXML
    private TableView<PressureMeasurement> tableView;

    public VisualizzazioneStoricoPressioniController(UserSession userSession, PressureMeasurementRepository pressureMeasurementRepository, StageManager stageManager) {
        this.userSession = userSession;
        this.pressureMeasurementRepository = pressureMeasurementRepository;
        this.stageManager = stageManager;
    }

    public void goBackHome() {
        stageManager.switchScene(FXMLView.HOME_PAGE_DOCTOR);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<PressureMeasurement> list = pressureMeasurementRepository.findAllByPatient(userSession.getPatient());

        TableColumn<PressureMeasurement, LocalDateTime> timestamp = new TableColumn<>("Data e ora");
        timestamp.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDateTime()));
        timestamp.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setText(null);
                else setText(String.format(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
            }
        });
        tableView.getColumns().add(timestamp);

        TableColumn<PressureMeasurement, Integer> sistolic = new TableColumn<>("Pressione sistolica");
        sistolic.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSistolicPressure()));
        tableView.getColumns().add(sistolic);

        TableColumn<PressureMeasurement, Integer> distolic = new TableColumn<>("Pressione distolica");
        distolic.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDiastolicPressure()));
        tableView.getColumns().add(distolic);

        tableView.setItems(FXCollections.observableList(list));
    }
}
