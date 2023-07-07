package it.univr.ipertesi;

import it.univr.ipertesi.utils.FXMLView;
import it.univr.ipertesi.utils.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IpertesiApplication extends Application {
    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(String[]::new);
        springContext = SpringApplication.run(IpertesiApplication.class, args);
    }

    @Override
    public void start(Stage stage) {
        StageManager stageManager = springContext.getBean(StageManager.class, stage);
        stageManager.switchScene(FXMLView.LOGIN);
        stage.show();
    }

    @Override
    public void stop() {
        springContext.stop();
    }
}
