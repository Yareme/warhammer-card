package pl.edu.us.warhammer_card.table;

public class Pancerz {

    private int id;
    private String nazwa;
    private String cena;
    private String kara;

    private String lokalizacja_pancerza;
    private int punktyPancerza;
    private String zalety;
    private String wady;

    private int dostepnoscId;
    private int typPancerzaId;


    private String dastepnosc;
    private String typPancerza;


    private int czyZalozone;

    public int getCzyZalozone() {
        return czyZalozone;
    }

    public void setCzyZalozone(int czyZalozone) {
        this.czyZalozone = czyZalozone;
    }

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

    public String getKara() {
        return kara;
    }

    public void setKara(String kara) {
        this.kara = kara;
    }

    public String getLokalizacja_pancerza() {
        return lokalizacja_pancerza;
    }

    public void setLokalizacja_pancerza(String lokalizacja_pancerza) {
        this.lokalizacja_pancerza = lokalizacja_pancerza;
    }

    public int getPunktyPancerza() {
        return punktyPancerza;
    }

    public void setPunktyPancerza(int punktyPancerza) {
        this.punktyPancerza = punktyPancerza;
    }

    public String getZalety() {
        return zalety;
    }

    public void setZalety(String zalety) {
        this.zalety = zalety;
    }

    public String getWady() {
        return wady;
    }

    public void setWady(String wady) {
        this.wady = wady;
    }

    public int getDostepnoscId() {
        return dostepnoscId;
    }

    public void setDostepnoscId(int dostepnoscId) {
        this.dostepnoscId = dostepnoscId;
    }

    public int getTypPancerzaId() {
        return typPancerzaId;
    }

    public void setTypPancerzaId(int typPancerzaId) {
        this.typPancerzaId = typPancerzaId;
    }

    public String getDastepnosc() {
        return dastepnosc;
    }

    public void setDastepnosc(String dastepnosc) {
        this.dastepnosc = dastepnosc;
    }

    public String getTypPancerza() {
        return typPancerza;
    }

    public void setTypPancerza(String typPancerza) {
        this.typPancerza = typPancerza;
    }
}
