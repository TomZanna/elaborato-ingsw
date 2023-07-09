package it.univr.ipertesi.utils;

public enum FXMLView {
    LOGIN("/views/loginPage.fxml"),
    HOME_PAGE_PATIENT("/views/homePagePaziente.fxml"),
    HOME_PAGE_DOCTOR("/views/homePageMedico.fxml"),
    INSERIMENTO_DATI("/views/inserimentoDati.fxml"),
    ASSUNZIONE_FARMACI("/views/inserimentoAssunzioneFarmaciPaziente.fxml");

    public final String fxmlPath;

    FXMLView(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }
}
