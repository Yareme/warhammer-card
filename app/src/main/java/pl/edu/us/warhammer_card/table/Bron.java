package pl.edu.us.warhammer_card.table;

import java.util.List;

public class Bron {


    private int id;

    private String nazwa;
    private String cena;
    private String zasieg;
    private String obrazenia;

    private  int obciazenie;

    private int czyWlasna;

    private int typBroniId;
    private int dostepnoscBroniID;

    private String dostemnoscText;

    public String getDostemnoscText() {
        return dostemnoscText;
    }

    public void setDostemnoscText(String dostemnoscText) {
        this.dostemnoscText = dostemnoscText;
    }

    private TypBroni typBroni;

    public TypBroni getTypBroni() {
        return typBroni;
    }

    public void setTypBroni(TypBroni typBroni) {
        this.typBroni = typBroni;
        this.typBroniId = typBroni.getId();
    }

    private List<CechaBroni> listaCech;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getZasieg() {
        return zasieg;
    }

    public void setZasieg(String zasieg) {
        this.zasieg = zasieg;
    }

    public String getObrazenia() {
        return obrazenia;
    }

    public void setObrazenia(String obrazenia) {
        this.obrazenia = obrazenia;
    }

    public int getObciazenie() {
        return obciazenie;
    }

    public void setObciazenie(int obciazenie) {
        this.obciazenie = obciazenie;
    }

    public int getCzyWlasna() {
        return czyWlasna;
    }

    public void setCzyWlasna(int czyWlasna) {
        this.czyWlasna = czyWlasna;
    }

    public int getTypBroniId() {
        return typBroniId;
    }

    public void setTypBroniId(int typBroniId) {
        this.typBroniId = typBroniId;
    }

    public List<CechaBroni> getListaCech() {
        return listaCech;
    }

    public int getDostepnoscBroniID() {
        return dostepnoscBroniID;
    }

    public void setDostepnoscBroniID(int dostepnoscBroniID) {
        this.dostepnoscBroniID = dostepnoscBroniID;
    }

    public void setListaCech(List<CechaBroni> listaCech) {
        this.listaCech = listaCech;
    }
}
