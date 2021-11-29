package id.ac.umn.test3;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SourcePlanner {
    public static ArrayList<SourcePlanner> sourcePlannerArrayList = new ArrayList<>();
    public static String NOTE_EDIT_EXTRA = "noteEdit";
    private int id;
    private String judul;
    private String deskripsi;
    private String time;
    private LocalDate date;
    private String address;

    public SourcePlanner(Integer id, String judul, String deskripsi, String time, LocalDate date, String address){
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
        this.date = date;
        this.address = address;
    }

    public SourcePlanner(Integer id, String judul, String deskripsi, String time, LocalDate date){
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
        this.date = date;
        address = "";
    }

    public SourcePlanner(Integer id, String judul, String deskripsi, String time){
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
        date = CalendarUtils.selectedDate;
        address = "";
    }

    public static SourcePlanner getPlanForID(int passedPlanID) {
        for (SourcePlanner plan: sourcePlannerArrayList) {
            if (plan.getId() == passedPlanID)
                return plan;
        }
        return null;
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
    public int getId() {
        return id;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setId(int id) {
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
}
