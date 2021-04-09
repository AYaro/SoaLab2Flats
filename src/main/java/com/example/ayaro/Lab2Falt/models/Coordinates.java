package com.example.ayaro.Lab2Falt.models;

public class Coordinates {
    private Long x; //Значение поля должно быть больше -484, Поле не может быть null
    private Double y; //Поле не может быть null

    public Coordinates(Long x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }
}

