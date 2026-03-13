package pl.edu.us.warhammer_card.table;

public class Profesja {
    int id;
    String nazawa;
    String sciezkaProfesji;
    int klasaId;

    Klasa klasa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazawa() {
        return nazawa;
    }

    public void setNazawa(String nazawa) {
        this.nazawa = nazawa;
    }

    public int getKlasaId() {
        return klasaId;
    }

    public void setKlasaId(int klasaId) {
        this.klasaId = klasaId;
    }

    public String getSciezkaProfesji() {
        return sciezkaProfesji;
    }

    public void setSciezkaProfesji(String sciezkaProfesji) {
        this.sciezkaProfesji = sciezkaProfesji;
    }

    public Klasa getKlasa() {
        return klasa;
    }

    public void setKlasa(Klasa klasa) {
        this.klasa = klasa;
    }
}
