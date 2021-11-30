package id.ac.umn.test3;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SourcePlanner {
    public static ArrayList<SourcePlanner> sourcePlannerArrayList = new ArrayList<>();
//    public static String NOTE_EDIT_EXTRA = "noteEdit";
//    private int id;
    private String judul, id, deskripsi,time;
    private String date;
    private String address;
    private String imageURL;

    public SourcePlanner(){}

    public SourcePlanner(String id, String judul, String deskripsi, String time, String date, String address, String imageUrl){
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
        this.date = date;
        this.address = address;
        this.imageURL = imageUrl;
    }

    public SourcePlanner(String id, String judul, String deskripsi, String time, String date, String address){
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
        this.date = date;
        this.address = address;
    }

    public SourcePlanner(String id, String judul, String deskripsi, String time, String  date){
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
    public String getDate() {
        return date;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDate(String date) {
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
