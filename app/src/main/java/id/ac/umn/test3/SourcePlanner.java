package id.ac.umn.test3;

import java.time.LocalDate;
import java.util.ArrayList;

public class SourcePlanner {
    public static ArrayList<SourcePlanner> sourcePlannerArrayList = new ArrayList<>();
    private int id;
    private String judul;
    private String deskripsi;
    private String time;
    private LocalDate date;

    public static ArrayList<SourcePlanner> eventsForDate(LocalDate date) {
        ArrayList<SourcePlanner> sourcePlanners = new ArrayList<>();
        for (SourcePlanner sourcePlanner: sourcePlannerArrayList) {
            if (sourcePlanner.getDate().equals(date))
                sourcePlanners.add(sourcePlanner);
        }
        return sourcePlanners;
    }

    public SourcePlanner(String judul, String deskripsi, String time, LocalDate date){
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
        this.date = date;
    }

    public SourcePlanner(String judul, String deskripsi, String time){
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
        date = CalendarUtils.selectedDate;
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
//    public String toString() {
//        return this.getJudul() + " => " + this.getDeskripsi();
//    }
}
