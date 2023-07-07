package it.univr.ipertesi.utils;

import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AppJavaConfig {

    final SpringFXMLLoader springFXMLLoader;

    @Autowired
    public AppJavaConfig(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    @Bean
    @Lazy
    public StageManager stageManager(Stage stage) {
        return new StageManager(springFXMLLoader, stage);
    }
}
