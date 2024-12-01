package org.example.models;

public class Meccs {
    private Long id;
    private String datum;
    private String kezdes;
    private String tipus;
    private int belepo;

    public Meccs() {
    }

    public Meccs(Long id, String datum, String kezdes, String tipus, int belepo) {
        this.id = id;
        this.datum = datum;
        this.kezdes = kezdes;
        this.tipus = tipus;
        this.belepo = belepo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getBelepo() {
        return belepo;
    }

    public void setBelepo(int belepo) {
        this.belepo = belepo;
    }

    @Override
    public String toString() {
        return  id +" | "+ datum +" " + kezdes;
    }
}