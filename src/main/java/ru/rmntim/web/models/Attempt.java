package ru.rmntim.web.models;

import java.io.Serializable;
import java.util.Date;

public class Attempt implements Serializable {
    private Double x;
    private Double y;
    private Double r;
    private Boolean result;


    // Конструкторы
    public Attempt() {}

    public Attempt(Double x, Double y, Double r, Boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    public Attempt(Double x, Double y, Double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    // Геттеры и сеттеры
    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }

    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }

    public Double getR() { return r; }
    public void setR(Double r) { this.r = r; }

    public Boolean getResult() { return result; }
    public void setResult(Boolean result) { this.result = result; }
}