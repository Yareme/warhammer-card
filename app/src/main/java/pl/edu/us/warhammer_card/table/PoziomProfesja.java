package pl.edu.us.warhammer_card.table;

public class PoziomProfesja {


    int id;
    String nazwa;
    int profesjaId;
    int statusId;
    String schemat_cech;

    int[] schematCechTabel;

    public int[] getSchematCechTabel() {
        return schematCechTabel;
    }

    public void setSchematCechTabel(int[] schematCechTabel) {
        this.schematCechTabel = schematCechTabel;
    }


    public String getSchemat_cech() {
        return schemat_cech;
    }

    public void setSchemat_cech(String schemat_cech) {

        this.schematCechTabel=convertStringToArray(schemat_cech);
        this.schemat_cech = schemat_cech;
    }
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

    public int getProfesjaId() {
        return profesjaId;
    }

    public void setProfesjaId(int profesjaId) {
        this.profesjaId = profesjaId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }



    public static int[] convertStringToArray(String str) {
        String[] strArray = str.split(",");
        int[] intArray = new int[strArray.length];

        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }

        return intArray;
    }
}
