package com.example.ayaro.Lab2Falt.models;

import java.util.ArrayList;
import java.util.Arrays;

public enum Transport {
    FEW("few"),
    NONE("none"),
    NORMAL("normal"),
    ENOUGH("enough");

    private final String name;

    Transport(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<String> getAll() {
        return new ArrayList<>(Arrays.asList("few", "none", "normal", "enough"));
    }

    public static Transport getByName(String name) {
        switch (name.toLowerCase()) {
            case "few": return FEW;
            case "none": return NONE;
            case "normal": return NORMAL;
            case "enough": return ENOUGH;
            default: return null;
        }
    }
}
