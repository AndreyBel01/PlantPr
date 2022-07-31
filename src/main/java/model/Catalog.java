package model;

import java.time.LocalDate;
import java.util.List;

public class Catalog {

    private String uuid;
    private LocalDate date;
    private String company;
    private List<Plant> plant;

    public void setPlant(List<Plant> plant) {
        this.plant = plant;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "uuid='" + uuid + '\'' +
                ", date=" + date +
                ", company='" + company + '\'' +
                ", plant=" + plant +
                '}';
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCompany() {
        return company;
    }

}
