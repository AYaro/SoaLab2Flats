package com.example.ayaro.Lab2Falt;

import com.example.ayaro.Lab2Falt.models.Flat;
import com.example.ayaro.Lab2Falt.models.Furnish;
import com.example.ayaro.Lab2Falt.models.Transport;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class Service {
    public static Flat makeFlatFromParams(Map<String, String> parameters)
            throws ParseException, NumberFormatException {
        String name = parameters.get("name");
        Long x = Long.parseLong(parameters.get("coordinateX"));
        Double y = Double.parseDouble(parameters.get("coordinateY"));
        LocalDateTime creationDate = LocalDateTime.now();
        Integer area = Integer.parseInt(parameters.get("area"));
        int numberOfRooms = Integer.parseInt(parameters.get("numberOfRooms"));
        Integer height = Integer.parseInt(parameters.get("height"));
        String furnishType = parameters.get("furnish");
        String transportType = parameters.get("transport");
        String houseName = null;
        if (parameters.get("houseName") != null) {
            houseName = parameters.get("houseName");
        }
        Integer year = Integer.parseInt(parameters.get("year"));
        int numberOfLifts = Integer.parseInt(parameters.get("numberOfLifts"));
        int price = 0;
        if (parameters.get("price") != null) {
            price = Integer.parseInt(parameters.get("price"));
        }
        if (area <= 0 || year <= 0 || numberOfRooms <= 0 || height <= 0 || numberOfLifts <= 0 || x <= -484){
            throw new NumberFormatException();
        }
        return new Flat(name, x, y, creationDate, area, numberOfRooms,
                height, furnishType, transportType, houseName, year, numberOfLifts, price);
    }

    public static Flat updateFlatFromParams(Map<String, String> parameters, Flat flat)
            throws ParseException, NumberFormatException {
        if (parameters.get("name") != null) {
            flat.setName(parameters.get("name"));
        }
        if (parameters.get("coordinateX") != null) {
            flat.setCoordinateX(Long.parseLong(parameters.get("coordinateX")));
        }
        if (parameters.get("coordinateY") != null) {
            flat.setCoordinateY(parameters.get("coordinateY").isEmpty() ? 0
                    : Double.parseDouble(parameters.get("coordinateY")));
        }
        if (parameters.get("area") != null) {
            flat.setArea(Integer.parseInt(parameters.get("area")));
        }
        if (parameters.get("numberOfRooms") != null) {
            flat.setNumberOfRooms(Integer.parseInt(parameters.get("numberOfRooms")));
        }
        if (parameters.get("height") != null) {
            flat.setHeight(Integer.parseInt(parameters.get("height")));
        }
        if (parameters.get("furnish") != null) {
            flat.setFurnish(Furnish.getByName(parameters.get("furnish")));
        }
        if (parameters.get("transport") != null) {
            flat.setTransport(Transport.getByName(parameters.get("transport")));
        }
        if (parameters.get("houseName") != null) {
            flat.setHouseName(parameters.get("houseName"));
        }
        if (parameters.get("houseName") == null) {
            flat.setHouseName(null);
        }
        if (parameters.get("year") != null) {
            flat.setHouseYear(Integer.parseInt(parameters.get("year")));
        }
        if (parameters.get("numberOfLifts") != null) {
            flat.setHouseYear(Integer.parseInt(parameters.get("numberOfLifts")));
        }
        if (parameters.get("price") != null) {
            flat.setPrice(Integer.parseInt(parameters.get("price")));
        }
        return flat;
    }


    public static Flat addFlat(Flat flat) throws SQLException {
        Store.addFlat(Objects.requireNonNull(Store.getConnection()), flat);
        return flat;
    }

    public static int countFlatsByHouse(String name, Long year, int numberOfLifts) throws SQLException {
        return Store.CountHouse(Store.getConnection(), name, year, numberOfLifts);
    }

    public static int countFlatsByTransport(String transport) throws SQLException {
        return Store.CountWhereMore(Store.getConnection(), transport);
    }

    public static void deleteOneByRoom(int numberOfRooms) throws SQLException {
        Store.DeleteOneByRoom(Store.getConnection(), numberOfRooms);
    }

    public static Flat getFlatById(long id) throws SQLException {
        return Store.getFlatById(Objects.requireNonNull(Store.getConnection()), id);
    }

    public static Flat updateFlat(long id, Flat flat) throws SQLException {
        flat.setId(id);
        flat = Store.updateFlat(Objects.requireNonNull(Store.getConnection()), flat);
        return flat;
    }

    public static void deleteFlat(long id) throws SQLException {
        Store.deleteFlat(Objects.requireNonNull(Store.getConnection()), id);
    }

    public static ArrayList<Flat> getAllFlat() throws SQLException {
        return Store.getAllFlats(Objects.requireNonNull(Store.getConnection()));
    }


    public static ArrayList<Flat> getFlat(String[] filterFields, String[] filterValues, String[] sortFields,
                                               int limit, int offset) throws SQLException {
        return Store.getFlats(Store.getConnection(), filterFields, filterValues, sortFields, offset, limit);
    }
}



