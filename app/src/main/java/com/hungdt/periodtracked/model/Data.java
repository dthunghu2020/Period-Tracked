package com.hungdt.periodtracked.model;

import java.util.List;

public class Data {
    private String id;
    private String day;
    private String typeDay;
    private String idMotion;
    private String idSymptom;
    private String idPhysic;
    private String idOvulation;
    private float weight;
    private int hour;
    private int minutes;
    private float water;

    public Data(String id, String day, String typeDay, String idMotion, String idSymptom, String idPhysic, String idOvulation, float weight, int hour, int minutes, float water) {
        this.id = id;
        this.day = day;
        this.typeDay = typeDay;
        this.idMotion = idMotion;
        this.idSymptom = idSymptom;
        this.idPhysic = idPhysic;
        this.idOvulation = idOvulation;
        this.weight = weight;
        this.hour = hour;
        this.minutes = minutes;
        this.water = water;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public float getWater() {
        return water;
    }

    public void setWater(float water) {
        this.water = water;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTypeDay() {
        return typeDay;
    }

    public void setTypeDay(String typeDay) {
        this.typeDay = typeDay;
    }

    public String getIdMotion() {
        return idMotion;
    }

    public void setIdMotion(String idMotion) {
        this.idMotion = idMotion;
    }

    public String getIdSymptom() {
        return idSymptom;
    }

    public void setIdSymptom(String idSymptom) {
        this.idSymptom = idSymptom;
    }

    public String getIdPhysic() {
        return idPhysic;
    }

    public void setIdPhysic(String idPhysic) {
        this.idPhysic = idPhysic;
    }

    public String getIdOvulation() {
        return idOvulation;
    }

    public void setIdOvulation(String idOvulation) {
        this.idOvulation = idOvulation;
    }
}
