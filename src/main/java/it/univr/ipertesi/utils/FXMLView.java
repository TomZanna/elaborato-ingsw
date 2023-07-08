package it.univr.ipertesi.utils;

public enum FXMLView {
    LOGIN("/views/loginPage.fxml"),
    CITIZEN_SERVICES("/views/citizienServicePage.fxml"),
    INSERT_AVAILABILITY("/views/insertAvailabiltyPage.fxml"),
    HOME_PAGE("/views/homePagePaziente.fxml"),
    HOME_PAGE_MEDICO("/views/homePageMedico.fxml"),
    INSERIMENTO_DATI("/views/inserimentoDati.fxml");

    public final String fxmlPath;

    FXMLView(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }
}
