package org.example.models;


public class DataRow {
    private long belepesId;
    private long nezoId;
    private long meccsId;
    private String idopont;
    private String nev;
    private boolean ferfi;
    private boolean berletes;
    private boolean ferfiBoolean;
    private boolean berletesBoolean;
    private String datum;
    private String kezdes;
    private int belepo;
    private String tipus;

    public DataRow(long belepesId, long nezoId, long meccsId, String idopont,
                   String nev, boolean ferfi, boolean berletes, boolean ferfiBoolean,
                   boolean berletesBoolean, String datum, String kezdes, int belepo, String tipus) {
        this.belepesId = belepesId;
        this.nezoId = nezoId;
        this.meccsId = meccsId;
        this.idopont = idopont;
        this.nev = nev;
        this.ferfi = ferfi;
        this.berletes = berletes;
        this.ferfiBoolean = ferfiBoolean;
        this.berletesBoolean= berletesBoolean;
        this.datum = datum;
        this.kezdes = kezdes;
        this.belepo = belepo;
        this.tipus = tipus;
    }

    public long getBelepesId() {
        return belepesId;
    }

    public void setBelepesId(long belepesId) {
        this.belepesId = belepesId;
    }

    public long getNezoId() {
        return nezoId;
    }

    public void setNezoId(long nezoId) {
        this.nezoId = nezoId;
    }

    public long getMeccsId() {
        return meccsId;
    }

    public void setMeccsId(long meccsId) {
        this.meccsId = meccsId;
    }

    public String getIdopont() {
        return idopont;
    }

    public void setIdopont(String idopont) {
        this.idopont = idopont;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public boolean isFerfi() {
        return ferfi;
    }

    public void setFerfi(boolean ferfi) {
        this.ferfi = ferfi;
    }


    public boolean isBerletes() {
        return berletes;
    }

    public void setBerletes(boolean berletes) {
        this.berletes = berletes;
    }

    public boolean isFerfiBoolean() {
        return ferfiBoolean;
    }

    public void setFerfiBoolean(boolean ferfiBoolean) {
        this.ferfiBoolean = ferfiBoolean;
    }

    public boolean isBerletesBoolean() {
        return berletesBoolean;
    }

    public void setBerletesBoolean(boolean berletesBoolean) {
        this.berletesBoolean = berletesBoolean;
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

    public int getBelepo() {
        return belepo;
    }

    public void setBelepo(int belepo) {
        this.belepo = belepo;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
}
