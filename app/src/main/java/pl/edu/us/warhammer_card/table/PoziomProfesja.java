package pl.edu.us.warhammer_card.table;

public class PoziomProfesja {

    int id;
    String nazwa;
    int profesjaId;
    int statusId;
    String schematCech;

    String schematUmiejetnosci;
    String schematTalentow;

    int[] schematCechTabel;
    int[] schematUmiejetnosciTabel;
    int[] schematTalentowTabel;


    Profesja profesja;
    Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Profesja getProfesja() {
        return profesja;
    }

    public void setProfesja(Profesja profesja) {
        this.profesja = profesja;
    }

    public String getSchematUmiejetnosci() {
        return schematUmiejetnosci;
    }

    public void setSchematUmiejetnosci(String schematUmiejetnosci) {
        this.schematTalentowTabel=convertStringToArray(schematUmiejetnosci);

        this.schematUmiejetnosci = schematUmiejetnosci;
    }

    public String getSchematTalentow() {
        return schematTalentow;
    }

    public void setSchematTalentow(String schematTalentow) {
        this.schematTalentowTabel=convertStringToArray(schematTalentow);

        this.schematTalentow = schematTalentow;
    }

    public int[] getSchematUmiejetnosciTabel() {
        return schematUmiejetnosciTabel;
    }

    public void setSchematUmiejetnosciTabel(int[] schematUmiejetnosciTabel) {
        this.schematUmiejetnosciTabel = schematUmiejetnosciTabel;
    }

    public int[] getSchematTalentowTabel() {
        return schematTalentowTabel;
    }

    public void setSchematTalentowTabel(int[] schematTalentowTabel) {
        this.schematTalentowTabel = schematTalentowTabel;
    }

    public int[] getSchematCechTabel() {
        return schematCechTabel;
    }

    public void setSchematCechTabel(int[] schematCechTabel) {
        this.schematCechTabel = schematCechTabel;
    }


    public String getSchematCech() {
        return schematCech;
    }

    public void setSchematCech(String schematCech) {

        this.schematCechTabel=convertStringToArray(schematCech);
        this.schematCech = schematCech;
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
