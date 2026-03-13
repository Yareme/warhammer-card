package pl.edu.us.warhammer_card.table;

import java.util.List;

public class Karta {

    private int id;
    private String imie ="";

    private  String wiek;
    private  String wzrost;
    private  String wlosy;
    private  String oczy;

    private int punktyPrzeznaczenia;
    private int punktySzczescia;
    private int punktyBohatera;
    private int punktyDeterminacji;
    private int punktyDodatkowe;
    private int szybkosc;
    private int xpAktualny;
    private int xpWydany;
    private int pensy;
    private int sreblo;
    private int zlotaKorona;
    private int punktyGrzechu;

    private String psyhologia;
    private String motywacja;
    private String zepsucieIMutacje;
    private String ambicjaKrotkoterminowa;
    private String ambicjaDlugoterminowa;
    private String nazwaDruzyny;
    private String ambicjaDruzynowaKrotkoterm;
    private String ambicjaDruzynowaDlugoterm;
    private String czlonkowieDruzyny;
    private List<Cechy> cechyList;
    private List<Umiejetnosci> umiejetnosciList;
    private List<Umiejetnosci> umiejetnosciZaawansowaneList;
    private List<Talent> talentList;
    private List<Wyposarzenia> wyposarzeniaList;
    private List<Pancerz> pancerzList;

    private List<Bron> bronList;
    private List<Zaklecia> zakleciaList;

    private Rasa rasa;

    private PoziomProfesja poziomProfesja;

    private Profesja profesja;

    public List<Bron> getBronList() {
        return bronList;
    }

    public void setBronList(List<Bron> bronList) {
        this.bronList = bronList;
    }

    public List<Umiejetnosci> getUmiejetnosciZaawansowaneList() {
        return umiejetnosciZaawansowaneList;
    }

    public void setUmiejetnosciZaawansowaneList(List<Umiejetnosci> umiejetnosciZaawansowaneList) {
        this.umiejetnosciZaawansowaneList = umiejetnosciZaawansowaneList;
    }

    public List<Pancerz> getPancerzList() {
        return pancerzList;
    }

    public void setPancerzList(List<Pancerz> pancerzList) {
        this.pancerzList = pancerzList;
    }

    public List<Umiejetnosci> getUmiejetnosciList() {
        return umiejetnosciList;
    }

    public void setUmiejetnosciList(List<Umiejetnosci> umiejetnosciList) {
        this.umiejetnosciList = umiejetnosciList;
    }

    public Profesja getProfesja() {
        return profesja;
    }

    public void setProfesja(Profesja profesja) {
        this.profesja = profesja;
    }

    public PoziomProfesja getPoziomProfesja() {
        return poziomProfesja;
    }

    public void setPoziomProfesja(PoziomProfesja poziomProfesja) {
        this.poziomProfesja = poziomProfesja;
    }

    public Rasa getRasa() {
        return rasa;
    }

    public void setRasa(Rasa rasa) {
        this.rasa = rasa;
    }

    public List<Wyposarzenia> getWyposarzeniaList() {
        return wyposarzeniaList;
    }

    public void setWyposarzeniaList(List<Wyposarzenia> wyposarzeniaList) {
        this.wyposarzeniaList = wyposarzeniaList;
    }

    public List<Zaklecia> getZakleciaList() {
        return zakleciaList;
    }

    public void setZakleciaList(List<Zaklecia> zakleciaList) {
        this.zakleciaList = zakleciaList;
    }

    public List<Talent> getTalentList() {
        return talentList;
    }

    public void setTalentList(List<Talent> talentList) {
        this.talentList = talentList;
    }

    public List<Cechy> getCechyList() {
        return cechyList;
    }

    public void setCechyList(List<Cechy> cechyList) {
        this.cechyList = cechyList;
    }

    private int zywotnoscAktualna;

    public int getZywotnoscAktualna() {
        return zywotnoscAktualna;
    }

    public void setZywotnoscAktualna(int zywotnoscAktualna) {
        this.zywotnoscAktualna = zywotnoscAktualna;
    }

    private int kampaniaId;
    private int rasaId;
    private int profesjaId;
    private int poziomProfesjiId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }


    public String getWiek() {
        return wiek;
    }

    public void setWiek(String wiek) {
        this.wiek = wiek;
    }

    public String getWzrost() {
        return wzrost;
    }

    public void setWzrost(String wzrost) {
        this.wzrost = wzrost;
    }

    public String getWlosy() {
        return wlosy;
    }

    public void setWlosy(String wlosy) {
        this.wlosy = wlosy;
    }

    public String getOczy() {
        return oczy;
    }

    public void setOczy(String oczy) {
        this.oczy = oczy;
    }

    public int getPunktyPrzeznaczenia() {
        return punktyPrzeznaczenia;
    }

    public void setPunktyPrzeznaczenia(int punktyPrzeznaczenia) {
        this.punktyPrzeznaczenia = punktyPrzeznaczenia;
    }

    public int getPunktySzczescia() {
        return punktySzczescia;
    }

    public void setPunktySzczescia(int punktySzczescia) {
        this.punktySzczescia = punktySzczescia;
    }

    public int getPunktyBohatera() {
        return punktyBohatera;
    }

    public void setPunktyBohatera(int punktyBohatera) {
        this.punktyBohatera = punktyBohatera;
    }

    public int getPunktyDeterminacji() {
        return punktyDeterminacji;
    }

    public void setPunktyDeterminacji(int punktyDeterminacji) {
        this.punktyDeterminacji = punktyDeterminacji;
    }

    public int getPunktyDodatkowe() {
        return punktyDodatkowe;
    }

    public void setPunktyDodatkowe(int punktyDodatkowe) {
        this.punktyDodatkowe = punktyDodatkowe;
    }

    public int getSzybkosc() {
        return szybkosc;
    }

    public void setSzybkosc(int szybkosc) {
        this.szybkosc = szybkosc;
    }

    public int getXpAktualny() {
        return xpAktualny;
    }

    public void setXpAktualny(int xpAktualny) {
        this.xpAktualny = xpAktualny;
    }

    public int getXpWydany() {
        return xpWydany;
    }

    public void setXpWydany(int xpWydany) {
        this.xpWydany = xpWydany;
    }

    public int getPensy() {
        return pensy;
    }

    public void setPensy(int pensy) {
        this.pensy = pensy;
    }

    public int getSreblo() {
        return sreblo;
    }

    public void setSreblo(int sreblo) {
        this.sreblo = sreblo;
    }

    public int getZlotaKorona() {
        return zlotaKorona;
    }

    public void setZlotaKorona(int zlotaKorona) {
        this.zlotaKorona = zlotaKorona;
    }

    public int getPunktyGrzechu() {
        return punktyGrzechu;
    }

    public void setPunktyGrzechu(int punktyGrzechu) {
        this.punktyGrzechu = punktyGrzechu;
    }

    public String getPsyhologia() {
        return psyhologia;
    }

    public void setPsyhologia(String psyhologia) {
        this.psyhologia = psyhologia;
    }

    public String getMotywacja() {
        return motywacja;
    }

    public void setMotywacja(String motywacja) {
        this.motywacja = motywacja;
    }

    public String getZepsucieIMutacje() {
        return zepsucieIMutacje;
    }

    public void setZepsucieIMutacje(String zepsucieIMutacje) {
        this.zepsucieIMutacje = zepsucieIMutacje;
    }

    public String getAmbicjaKrotkoterminowa() {
        return ambicjaKrotkoterminowa;
    }

    public void setAmbicjaKrotkoterminowa(String ambicjaKrotkoterminowa) {
        this.ambicjaKrotkoterminowa = ambicjaKrotkoterminowa;
    }

    public String getAmbicjaDlugoterminowa() {
        return ambicjaDlugoterminowa;
    }

    public void setAmbicjaDlugoterminowa(String ambicjaDlugoterminowa) {
        this.ambicjaDlugoterminowa = ambicjaDlugoterminowa;
    }

    public String getNazwaDruzyny() {
        return nazwaDruzyny;
    }

    public void setNazwaDruzyny(String nazwaDruzyny) {
        this.nazwaDruzyny = nazwaDruzyny;
    }

    public String getAmbicjaDruzynowaKrotkoterm() {
        return ambicjaDruzynowaKrotkoterm;
    }

    public void setAmbicjaDruzynowaKrotkoterm(String ambicjaDruzynowaKrotkoterm) {
        this.ambicjaDruzynowaKrotkoterm = ambicjaDruzynowaKrotkoterm;
    }

    public String getAmbicjaDruzynowaDlugoterm() {
        return ambicjaDruzynowaDlugoterm;
    }

    public void setAmbicjaDruzynowaDlugoterm(String ambicjaDruzynowaDlugoterm) {
        this.ambicjaDruzynowaDlugoterm = ambicjaDruzynowaDlugoterm;
    }

    public String getCzlonkowieDruzyny() {
        return czlonkowieDruzyny;
    }

    public void setCzlonkowieDruzyny(String czlonkowieDruzyny) {
        this.czlonkowieDruzyny = czlonkowieDruzyny;
    }

    public int getKampaniaId() {
        return kampaniaId;
    }

    public void setKampaniaId(int kampaniaId) {
        this.kampaniaId = kampaniaId;
    }

    public int getRasaId() {
        return rasaId;
    }

    public void setRasaId(int rasaId) {
        this.rasaId = rasaId;
    }

    public int getProfesjaId() {
        return profesjaId;
    }

    public void setProfesjaId(int profesjaId) {
        this.profesjaId = profesjaId;
    }

    public int getPoziomProfesjiId() {
        return poziomProfesjiId;
    }

    public void setPoziomProfesjiId(int poziomProfesjiId) {
        this.poziomProfesjiId = poziomProfesjiId;
    }
    @Override
    public String toString() {
        return "Karta -> " +
                " id= "+ id;
    }


}
