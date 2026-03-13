package pl.edu.us.warhammer_card.table;

public class TypBroni {

    private int id;
    private String nazwa;

    private int czyZaciegowa;

    private String opisSpecjalny;

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

    public int getCzyZaciegowa() {
        return czyZaciegowa;
    }

    public void setCzyZaciegowa(int czyZaciegowa) {
        this.czyZaciegowa = czyZaciegowa;
    }

    public String getOpisSpecjalny() {
        return opisSpecjalny;
    }

    public void setOpisSpecjalny(String opisSpecjalny) {
        this.opisSpecjalny = opisSpecjalny;
    }
}
