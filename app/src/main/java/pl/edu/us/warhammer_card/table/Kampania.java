package pl.edu.us.warhammer_card.table;

import android.icu.text.SimpleDateFormat;

import java.util.Date;

public class Kampania {

    private int id;
    private String nazwa;

    @Override
    public String toString() {
        Date d = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String f = sdf.format(d);



        return "Kampania {" +
                " id= "+ id +
                " nazwa='" + nazwa +
                " data= " + f+
                '\'' +

                '}';
    }
    private int date;



 /*   public Kampania(int id, String nazwa, int date) {
        this.id = id;
        this.nazwa = nazwa;
        this.date = date;
    }*/

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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

}
