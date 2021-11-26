package id.ac.umn.test3;

import java.io.Serializable;

public class SourcePlanner implements Serializable {
    private String judul;
    private String kategori;
    private String deskripsi;

    public SourcePlanner(String judul, String kategori, String deskripsi){
        this.judul = judul;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
    }
    public String getJudul() {
        return judul;
    }
    public String getKategori() {
        return kategori;
    }
    public String getDeskripsi() {
        return deskripsi;
    }
    public void setJudul(String judul){
        this.judul = judul;
    }
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
//    public String toString() {
//        return this.getJudul() + " => " + this.getDeskripsi();
//    }
}
