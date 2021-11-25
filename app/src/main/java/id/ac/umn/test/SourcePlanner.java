package id.ac.umn.test;

import java.io.Serializable;

public class SourcePlanner implements Serializable {
    private String judul;
    private String kategori;
    private String tanggal;

    public SourcePlanner(String judul, String kategori, String deskripsi){
        this.judul = judul;
        this.kategori = kategori;
        this.tanggal = tanggal;
    }
    public String getJudul() {
        return judul;
    }
    public String getKategori() {
        return kategori;
    }
    public String getTanggal() {
        return tanggal;
    }
    public void setJudul(String judul){
        this.judul = judul;
    }
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
//    public String toString() {
//        return this.getJudul() + " => " + this.getDeskripsi();
//    }
}

