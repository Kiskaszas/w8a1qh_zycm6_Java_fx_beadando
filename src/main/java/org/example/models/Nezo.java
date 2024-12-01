package org.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Nezo implements Serializable {

    private Long id;

    private String nev;

    private boolean ferfi;

    private boolean berletes;

    private List<Belepes> belepes = new ArrayList<>();

    public Nezo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Belepes> getBelepesek() {
        return belepes;
    }

    public void setBelepesek(List<Belepes> belepes) {
        this.belepes = belepes;
    }

    @Override
    public String toString() {
        return "Nezo{" +
                "id=" + id +
                ", nev='" + nev + '\'' +
                ", ferfi=" + ferfi +
                ", berletes=" + berletes +
                '}';
    }
}