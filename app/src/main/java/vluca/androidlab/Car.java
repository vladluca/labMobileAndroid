package vluca.androidlab;

/**
 * Created by lucav on 11/9/2016.
 */

import java.util.Date;

public class Car {
    private int id;
    private String mark;
    private String model;
    private Date year;
    private int[] fuel;

    public Car(int id, String mark, String model, Date year, int[] fuel) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.year = year;
        this.fuel = fuel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public int[] getFuel() {
        return fuel;
    }

    public void setFuel(int[] fuel) {
        this.fuel = fuel;
    }
}
