package com.graysan.springbootrest.model;

public class Ogrenci {
    private long id;
    private String name;
    private long number;
    private long year;

    public Ogrenci() {
    }

    public Ogrenci(String name, long number, long year) {
        this.name = name;
        this.number = number;
        this.year = year;
    }

    public Ogrenci(long id, String name, long number, long year) {
        this.id = id;
        this.name = name;
        this.number = number;
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

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
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
                ", number=" + number +
                ", year=" + year +
                '}';
    }
}
