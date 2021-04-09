package com.example.ayaro.Lab2Falt.models;

import java.util.ArrayList;
import java.util.Arrays;

public enum Furnish {
    DESIGNER("designer"),
    NONE("none"),
    BAD("bad"),
    LITTLE("little");

    private final String name;

    Furnish(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<String> getAll() {
        return new ArrayList<>(Arrays.asList("designer", "none", "bad", "little"));
    }

    public static Furnish getByName(String name) {
        switch (name.toLowerCase()) {
            case "designer": return DESIGNER;
            case "none": return NONE;
            case "bad": return BAD;
            case "little": return LITTLE;
            default: return null;
        }
    }
}