package pl.edu.us.warhammer_card.table;

public class Zaklecia {

    private int id;
    private String nazwa;
    private int poziomZaklecie;
    private String zacieg;
    private String cel;
    private String czas;
    private String opis;

    private int tradycjaId;

    private String tradycjaName;

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

    public int getPoziomZaklecie() {
        return poziomZaklecie;
    }

    public void setPoziomZaklecie(int poziomZaklecie) {
        this.poziomZaklecie = poziomZaklecie;
    }

    public String getZacieg() {
        return zacieg;
    }

    public void setZacieg(String zacieg) {
        this.zacieg = zacieg;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getCzas() {
        return czas;
    }

    public void setCzas(String czas) {
        this.czas = czas;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getTradycjaId() {
        return tradycjaId;
    }

    public void setTradycjaId(int tradycjaId) {
        this.tradycjaId = tradycjaId;
    }

    public String getTradycjaName() {
        return tradycjaName;
    }

    public void setTradycjaName(String tradycjaName) {
        this.tradycjaName = tradycjaName;
    }
}
