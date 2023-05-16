package it.questura.passaporti.utils;

public enum FXMLView {
    LOGIN("/views/loginPage.fxml"),
    CITIZEN_SERVICES("/views/citizienServicePage.fxml");

    public final String fxmlPath;

    FXMLView(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }
}
