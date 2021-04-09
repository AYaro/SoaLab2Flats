package com.example.ayaro.Lab2Falt.models;

public class House {
    private String name; //Поле может быть null
    private Integer year; //Значение поля должно быть больше 0
    private int numberOfLifts; //Значение поля должно быть больше 0

    public House(String name, Integer year, int numberOfLifts) {
        this.name = name;
        this.year = year;
        this.numberOfLifts = numberOfLifts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public int getNumberOfLifts() {
        return numberOfLifts;
    }

    public void setNumberOfLifts(int numberOfLifts) {
        this.numberOfLifts = numberOfLifts;
    }
}