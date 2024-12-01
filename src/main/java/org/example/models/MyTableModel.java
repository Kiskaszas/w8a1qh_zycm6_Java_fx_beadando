package org.example.models;

public class MyTableModel {
    private String datum;
    private String kezdes;
    private String tipus;

    public MyTableModel(String datum, String kezdes, String tipus) {
        this.datum = datum;
        this.kezdes = kezdes;
        this.tipus = tipus;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getKezdes() {
        return kezdes;
    }

    public void setKezdes(String kezdes) {
        this.kezdes = kezdes;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
}
