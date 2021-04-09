package com.example.ayaro.Lab2Falt.models;

import java.time.LocalDateTime;

public class Flat{
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer area; //Значение поля должно быть больше 0
    private int numberOfRooms; //Значение поля должно быть больше 0
    private Integer height; //Значение поля должно быть больше 0
    private Furnish furnish; //Поле не может быть null
    private Transport transport; //Поле не может быть null
    private House house; //Поле не может быть null
    private int price;

    public Flat(long id, String name, Long x, Double y, LocalDateTime creationDate, Integer area, int numberOfRooms, Integer height, String furnishType, String transportType, String houseName, Integer year, int numberOfLifts, int price) {
        this.id = id;
        this.name = name;
        this.coordinates = new Coordinates(x, y);
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.height = height;
        this.furnish = Furnish.getByName(furnishType);
        this.transport = Transport.getByName(transportType);
        this.house = new House(houseName, year, numberOfLifts);
        this.price = price;
    }

    public Flat(String name, Long x, Double y, LocalDateTime creationDate, Integer area, int numberOfRooms, Integer height, String furnishType, String transportType, String houseName, Integer year, int numberOfLifts, int price) {
        this.name = name;
        this.coordinates = new Coordinates(x, y);
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.height = height;
        this.furnish = Furnish.getByName(furnishType);
        this.transport = Transport.getByName(transportType);
        this.house = new House(houseName, year, numberOfLifts);
        this.price = price;
    }

    private void validate() {
        if (height <= 0) throw new IllegalArgumentException("height must be greater than 0");
        if (numberOfRooms <= 0) throw new IllegalArgumentException("numberOfRooms must be greater than 0");
        if (area <= 0) throw new IllegalArgumentException("area must be greater than 0");
        if (name== null || name.isEmpty()) throw new IllegalArgumentException("name must not be empty");
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCoordinateX(Long x) {
        this.coordinates.setX(x);
    }

    public void setCoordinateY(Double y) {
        this.coordinates.setY(y);
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Furnish getFurnish() {
        return furnish;
    }

    public void setFurnish(Furnish furnish) {
        this.furnish = furnish;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public House getHouse() {
        return house;
    }

    public void setHouseName(String name) {
        this.house.setName(name);
    }

    public void setHouseNumberOfLifts(int num) {
        this.house.setNumberOfLifts(num);
    }

    public void setHouseYear(Integer year) {
        this.house.setYear(year);
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
