package pl.edu.us.warhammer_card.table;

public class Wyposarzenia {

    private int id;
    private String nazwa;
    private int ociazenie;
    private int sztuk=0;

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

    public int getOciazenie() {
        return ociazenie;
    }

    public void setOciazenie(int ociazenie) {
        this.ociazenie = ociazenie;
    }

    public int getSztuk() {
        return sztuk;
    }

    public void setSztuk(int sztuk) {
        this.sztuk = sztuk;
    }
}
