package com.graysan.springbootrest.model;

public class Ogrenci {
    private long id;
    private String name;
    private long ogr_number;
    private long year;

    public Ogrenci() {
    }

    public Ogrenci(String name, long ogr_number, long year) {
        this.name = name;
        this.ogr_number = ogr_number;
        this.year = year;
    }

    public Ogrenci(long id, String name, long ogr_number, long year) {
        this.id = id;
        this.name = name;
        this.ogr_number = ogr_number;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOgr_number() {
        return ogr_number;
    }

    public void setOgr_number(long ogr_number) {
        this.ogr_number = ogr_number;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Ogrenci{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + ogr_number +
                ", year=" + year +
                '}';
    }
}
