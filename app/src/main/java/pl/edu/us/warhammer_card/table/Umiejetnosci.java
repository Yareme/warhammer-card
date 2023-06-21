package pl.edu.us.warhammer_card.table;

public class Umiejetnosci{

    int id;
    int karta_id;
    int rozwoj;
    int cecha_id;

    int wortascCecha;
    String nazwa;
    String cecha_nazwa;

    public int getWortascCecha() {
        return wortascCecha;
    }

    public void setWortascCecha(int wortascCecha) {
        this.wortascCecha = wortascCecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKarta_id() {
        return karta_id;
    }

    public void setKarta_id(int karta_id) {
        this.karta_id = karta_id;
    }

    public int getRozwoj() {
        return rozwoj;
    }

    public void setRozwoj(int rozwoj) {
        this.rozwoj = rozwoj;
    }

    public int getCecha_id() {
        return cecha_id;
    }

    public void setCecha_id(int cecha_id) {
        this.cecha_id = cecha_id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getCecha_nazwa() {
        return cecha_nazwa;
    }

    public void setCecha_nazwa(String cecha_nazwa) {
        this.cecha_nazwa = cecha_nazwa;
    }
}
