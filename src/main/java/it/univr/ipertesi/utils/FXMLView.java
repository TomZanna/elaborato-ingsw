package it.univr.ipertesi.utils;

public enum FXMLView {
    LOGIN("/views/loginPage.fxml"),
    CITIZEN_SERVICES("/views/citizienServicePage.fxml"),
    INSERT_AVAILABILITY("/views/insertAvailabiltyPage.fxml"),
    HOME_PAGE("/views/homePage.fxml");

    public final String fxmlPath;

    FXMLView(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }
}
