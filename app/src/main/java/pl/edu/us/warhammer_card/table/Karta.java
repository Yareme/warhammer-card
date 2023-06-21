package pl.edu.us.warhammer_card.table;

import android.icu.text.SimpleDateFormat;

import java.util.Date;

public class Karta {

    private int id;
    private String imie ="";

    private  String wiek;
    private  String wzrost;
    private  String wlosy;
    private  String oczy;

    private int pensy;
    private int sreblo;
    private int zlotaKorona;
    private int punktyGrzechu;

    private String psyhologia;
    private String zepsucieIMutacje;
    private String ambicjaKrotkoterminowa;
    private String ambicjaDlugoterminowa;
    private String nazwaDruzyny;
    private String ambicjaDruzynowaKrotkoterm;
    private String ambicjaDruzynowaDlugoterm;
    private String czlonkowieDruzyny;

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
