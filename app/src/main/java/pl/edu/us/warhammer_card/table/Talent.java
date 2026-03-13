package pl.edu.us.warhammer_card.table;

public class Talent {

    private int id;
    private String nazwa;
    private String maksimum;
    private String testy;
    private String opis;

    int poziom;

    public int getPoziom() {
        return poziom;
    }

    public void setPoziom(int poziom) {
        this.poziom = poziom;
    }

    int idCecha;

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

    public String getMaksimum() {
        return maksimum;
    }

    public void setMaksimum(String maksimum) {
        this.maksimum = maksimum;
    }

    public String getTesty() {
        return testy;
    }

    public void setTesty(String testy) {
        this.testy = testy;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getIdCecha() {
        return idCecha;
    }

    public void setIdCecha(int idCecha) {
        this.idCecha = idCecha;
    }
}
