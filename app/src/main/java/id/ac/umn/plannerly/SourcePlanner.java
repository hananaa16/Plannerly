package id.ac.umn.plannerly;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class SourcePlanner {
    public static ArrayList<SourcePlanner> sourcePlannerArrayList = new ArrayList<>();
//    public static String NOTE_EDIT_EXTRA = "noteEdit";
//    private int id;
    private String judul, id, deskripsi,time;
    private Timestamp date;
    private String address;
    private String imageURL;

    public SourcePlanner(){}

    public SourcePlanner(String id, String judul, String deskripsi, String time, Timestamp date, String address, String imageUrl){
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
        this.date = date;
        this.address = address;
        this.imageURL = imageUrl;
    }

    public SourcePlanner(String id, String judul, String deskripsi, String time, String imageURL, Timestamp date ){
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
        this.date = date;
        this.imageURL = imageURL;
    }

    public SourcePlanner(String id, String judul, String deskripsi, String time, Timestamp date, String address){
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
        this.date = date;
        this.address = address;
    }


    public SourcePlanner(String id, String judul, String deskripsi, String time, Timestamp date){
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
        this.date = date;

    }


    public String getJudul() {
        return judul;
    }
    public String getTime() {
        return time;
    }
    public String getDeskripsi() {
        return deskripsi;
    }
    public String getId() {
        return id;
    }
    public Timestamp getDate() {
        return date;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setJudul(String judul){
        this.judul = judul;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public String getImageURL() {
        return imageURL;
    }

}
