package it.univr.ipertesi.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SpringFXMLLoader {
    private final ApplicationContext context;

    @Autowired
    public SpringFXMLLoader(ApplicationContext context) {
        this.context = context;
    }

    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        // Uso Spring come controller factory
        loader.setControllerFactory(context::getBean);
        // Specifico il percorso della vista da caricare
        loader.setLocation(getClass().getResource(fxmlPath));

        return loader.load();
    }
}