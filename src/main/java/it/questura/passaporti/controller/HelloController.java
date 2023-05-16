package it.questura.passaporti.controller;

import it.questura.passaporti.model.Article;
import it.questura.passaporti.repository.ArticleRepository;
import it.questura.passaporti.utils.FXMLView;
import it.questura.passaporti.utils.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HelloController {
    private final ArticleRepository articleRepository;
    @FXML
    Button button;
    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    public HelloController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void clickHandler() {
        List<Article> art = articleRepository.findAll();
        System.out.println(art);
        stageManager.switchScene(FXMLView.CITIZEN_SERVICES);
    }
}
