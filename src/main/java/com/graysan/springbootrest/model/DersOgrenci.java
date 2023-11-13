package com.graysan.springbootrest.model;

public class DersOgrenci {
    private long ID;
    private long DERS_ID;
    private long OGRENCI_ID;
    private long devamsizlik;
    private long note;

    public DersOgrenci() {
    }

    public DersOgrenci(long ID, long DERS_ID, long OGR_ID, long devamsizlik, long note) {
        this.ID = ID;
        this.DERS_ID = DERS_ID;
        this.OGRENCI_ID = OGR_ID;
        this.devamsizlik = devamsizlik;
        this.note = note;
    }

    public DersOgrenci(long DERS_ID, long OGR_ID, long devamsizlik, long note) {
        this.DERS_ID = DERS_ID;
        this.OGRENCI_ID = OGR_ID;
        this.devamsizlik = devamsizlik;
        this.note = note;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getDERS_ID() {
        return DERS_ID;
    }

    public void setDERS_ID(long DERS_ID) {
        this.DERS_ID = DERS_ID;
    }

    public long getOGR_ID() {
        return OGRENCI_ID;
    }

    public void setOGR_ID(long OGR_ID) {
        this.OGRENCI_ID = OGR_ID;
    }

    public long getDevamsizlik() {
        return devamsizlik;
    }

    public void setDevamsizlik(long devamsizlik) {
        this.devamsizlik = devamsizlik;
    }

    public long getNote() {
        return note;
    }

    public void setNote(long note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "DersOgrenci{" +
                "ID=" + ID +
                ", DERS_ID=" + DERS_ID +
                ", OGR_ID=" + OGRENCI_ID +
                ", devamsizlik=" + devamsizlik +
                ", note=" + note +
                '}';
    }
}
