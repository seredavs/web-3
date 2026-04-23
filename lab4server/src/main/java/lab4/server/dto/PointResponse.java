package lab4.server.dto;

import java.time.LocalDateTime;

public class PointResponse {
    private Integer id;
    private Double x;
    private Double y;
    private Double r;
    private Boolean hit;
    private LocalDateTime date;

    public PointResponse() {
    }

    public PointResponse(Integer id, Double x, Double y, Double r, Boolean hit, LocalDateTime date) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getHit() {
        return hit;
    }

    public void setHit(Boolean hit) {
        this.hit = hit;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
