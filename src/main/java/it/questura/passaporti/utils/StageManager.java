package it.questura.passaporti.utils;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager {
    private final SpringFXMLLoader springFXMLLoader;
    private final Stage stage;

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.stage = stage;
    }

    public void switchScene(final FXMLView view) {
        // carico il nodo
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(view.fxmlPath);
        } catch (IOException e) {
            System.err.println("Impossibile caricare " + view.fxmlPath);
            Platform.exit();
        }

        // inserisco il nuovo nodo nella scena
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(rootNode);
            stage.setScene(scene);
        } else {
            scene.setRoot(rootNode);
        }
    }
}

