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

    public static ArrayList<SourcePlanner> eventsForDate(LocalDate date) {
        ArrayList<SourcePlanner> sourcePlanners = new ArrayList<>();
        for (SourcePlanner sourcePlanner: sourcePlannerArrayList) {
            if (sourcePlanner.getDate().equals(date))
                sourcePlanners.add(sourcePlanner);
        }
        return sourcePlanners;
    }

    public SourcePlanner(Integer id, String judul, String deskripsi, String time, String address, LocalDate date){
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
        address = "";
        date = CalendarUtils.selectedDate;
    }

    public static SourcePlanner getPlanForID(int passedPlanID) {
        for (SourcePlanner plan: sourcePlannerArrayList) {
            if (plan.getId() == passedPlanID)
                return plan;
        }
        return null;
    }

    public static ArrayList<SourcePlanner> nonDeletedPlans() {
        ArrayList<SourcePlanner> nonDeleted = new ArrayList<>();
        for (SourcePlanner plan : sourcePlannerArrayList) {
            if (plan.getDate() == null)
                nonDeleted.add(plan);
        }
        return nonDeleted;
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
//    public String toString() {
//        return this.getJudul() + " => " + this.getDeskripsi();
//    }
}
