package it.questura.passaporti.utils;

public enum FXMLView {
    LOGIN("/views/loginPage.fxml"),
    CITIZEN_REGISTRATION("/views/registationPage"),
    CITIZEN_SERVICES("/views/citizienServicePage.fxml"),
    INSERT_AVAILABILITY("/views/insertAvailabiltyPage.fxml");

    public final String fxmlPath;

    FXMLView(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }
}
