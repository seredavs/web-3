package lab4.server.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PointRequest {
    private Double x;
    private Double y;
    private Double r;

    public PointRequest() {
    }

    public PointRequest(Double x, Double y, Double r) {
        this.x = round(x);
        this.y = round(y);
        this.r = round(r);
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

    private Double round(Double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
