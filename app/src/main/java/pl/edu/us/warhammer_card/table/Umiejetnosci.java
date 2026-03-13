package pl.edu.us.warhammer_card.table;

public class Umiejetnosci{

    int id;
    int kartaId;

    int wortascCecha;
    int rozwoj;
    int suma;

    int cechaId;

    String nazwa;
    String cechaNazwa;

    Cechy cecha;

    public Cechy getCecha() {
        return cecha;
    }

    public void setCecha(Cechy cecha) {
        this.cecha = cecha;
        this.wortascCecha = cecha.getWartPo()+ cecha.getRozw();
        this.suma = wortascCecha + rozwoj;

    }

    public int getSuma() {
        return suma;
    }

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

    public int getKartaId() {
        return kartaId;
    }

    public void setKartaId(int kartaId) {
        this.kartaId = kartaId;
    }

    public int getRozwoj() {
        return rozwoj;
    }

    public void setRozwoj(int rozwoj) {
        this.rozwoj = rozwoj;
    }

    public int getCechaId() {
        return cechaId;
    }

    public void setCechaId(int cechaId) {
        this.cechaId = cechaId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getCechaNazwa() {
        return cechaNazwa;
    }

    public void setCechaNazwa(String cechaNazwa) {
        this.cechaNazwa = cechaNazwa;
    }
}
