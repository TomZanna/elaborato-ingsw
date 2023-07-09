package it.univr.ipertesi.model;

import javafx.scene.paint.Color;

public enum BloodPressure {
    NORMAL("Normale", Color.LIMEGREEN),
    HYPERTENSION_GRADE1("Ipertensione di grado 1", Color.ORANGE),
    HYPERTENSION_GRADE2("Ipertensione di grado 2", Color.DARKORANGE),
    HYPERTENSION_GRADE3("Ipertensione di grado 3", Color.INDIANRED),
    LIGHT_SYSTOLIC_HYPERTENSION("Ipertensione sistolica isolata borderline", Color.YELLOW),
    SYSTOLIC_HYPERTENSION("Ipertensione sistolica isolata", Color.ORANGE);

    public final String title;
    public final Color color;

    BloodPressure(String title, Color color) {
        this.title = title;
        this.color = color;
    }

    @Override
    public String toString() {
        return title;
    }
}
