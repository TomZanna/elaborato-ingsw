package it.univr.ipertesi.model;

public enum Symptom {
    EXHAUSTION(0,"spossatezza"),
    NAUSEA(1,"nausea"),
    HEADACHE(2,"mal di testa"),
    FEVER(3,"febbre"),
    OTHER(4,"altro");

    public final String title;
    public final int id;

    Symptom(int id, String title) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
