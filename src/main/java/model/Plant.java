package model;

public class Plant {

    private String common;
    private String botanical;
    private int zone;
    private String light;
    private double price;
    private int availability;

    public String getCommon() {
        return common;
    }

    public String getBotanical() {
        return botanical;
    }

    public int getZone() {
        return zone;
    }

    public String getLight() {
        return light;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailability() {
        return availability;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "common='" + common + '\'' +
                ", botanical='" + botanical + '\'' +
                ", zone=" + zone +
                ", light='" + light + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                '}';
    }

    public Plant(String common, String botanical, int zone, String light, double price, int availability) {
        this.common = common;
        this.botanical = botanical;
        this.zone = zone;
        this.light = light;
        this.price = price;
        this.availability = availability;
    }
}
