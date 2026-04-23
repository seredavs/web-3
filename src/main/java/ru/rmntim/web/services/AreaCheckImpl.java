package ru.rmntim.web.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("areaCheck")
@ApplicationScoped
public class AreaCheckImpl implements AreaCheck, Serializable {

    @Override
    public boolean checkHit(double x, double y, double r) {
        return (x >= 0 && y >= 0 && x*x + y*y <= r)
                || (x <= 0 && y >= 0 && x <= r/2 && y <= r)
                || (x <= 0 && y <= 0 && (-0.5) * x - r/2 <= y);
    }


    private boolean isPointInTriangle(double x, double y, double r) {

        double ax = 0, ay = 0;
        double bx = -r, by = 0;
        double cx = 0, cy = r/2;


        double areaABC = Math.abs((bx - ax) * (cy - ay) - (cx - ax) * (by - ay)) / 2.0;


        double areaPAB = Math.abs((ax - x) * (by - y) - (bx - x) * (ay - y)) / 2.0;
        double areaPBC = Math.abs((bx - x) * (cy - y) - (cx - x) * (by - y)) / 2.0;
        double areaPCA = Math.abs((cx - x) * (ay - y) - (ax - x) * (cy - y)) / 2.0;


        return Math.abs(areaPAB + areaPBC + areaPCA - areaABC) < 0.0001;
    }

    public boolean checkHitSimplified(double x, double y, double r) {
        // 1. Левый верхний прямоугольник (-R/2 <= x <= 0, 0 <= y <= R)
        if (x >= -r/2 && x <= 0 && y >= 0 && y <= r) {
            return true;
        }

        // 2. Правый верхний четверть круга (x >= 0, y >= 0, x² + y² <= R²)
        if (x >= 0 && y >= 0 && (x * x + y * y) <= (r * r)) {
            return true;
        }

        // 3. Левый нижний треугольник (0,0), (-R,0), (0,R/2)
        if (x <= 0 && y >= 0 && y <= 0.5 * (x + r) && x >= -r) {
            return true;
        }

        return false;
    }
}