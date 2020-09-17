package aston.carlocator.app;

import java.util.Date;

public class Locator {
    private int id;
    private String carName;
    private String date;
    private Double lng;
    private Double lat;

    public Locator(String carName, String date, Double lng, Double lat) {
        this.carName = carName;
        this.date = date;
        this.lng = lng;
        this.lat = lat;
    }

    public int getId() {
        return id;
    }

    public String getCarName() {
        return carName;
    }

    public String getDate() {
        return date;
    }

    public Double getLng() {
        return lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
