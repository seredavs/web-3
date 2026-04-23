package ru.rmntim.web.beans;

import ru.rmntim.web.database.ResultManager;
import ru.rmntim.web.models.Attempt;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("attempt")
@SessionScoped
public class AttemptBean implements Serializable {
    private Double x;
    private Double y;
    private Double r = 2.0;
    private Double graphX;
    private Double graphY;
    private List<Attempt> attemptList;
    private final ResultManager resultManager = new ResultManager();


    public AttemptBean() {
        System.out.println("[AttemptBean] Создан новый бин");
        this.attemptList = this.resultManager.getResults();
    }

    public void addFormPoint() {
        this.addPoint(this.x, this.y, this.r);
        System.out.println(this.x + " " + this.y + " " + this.r);
    }

    public void addGraphPoint() {
        this.addPoint(this.graphX, this.graphY, this.r);
        System.out.println(this.graphX + " " + this.graphY + " " + this.r);
    }

    private void addPoint(double x, double y, double r) {
        boolean hit = (x >= 0 && y >= 0 && x*x + y*y <= r)
                || (x <= 0 && y >= 0 && x <= r/2 && y <= r)
                || (x <= 0 && y <= 0 && (-0.5) * x - r/2 <= y);
        this.attemptList.add(new Attempt(x, y, r, hit));
        this.resultManager.insertResult(x, y, r, hit);

    }


    public void clear() {
        System.out.println("[AttemptBean] Очистка результатов");
        this.attemptList.clear();
    }

    // Геттеры и сеттеры
    public Double getX() {
        System.out.println("[AttemptBean] getX: " + x);
        return x;
    }

    public void setX(Double x) {
        System.out.println("[AttemptBean] setX: " + x);
        this.x = x;
    }

    public Double getY() {
        System.out.println("[AttemptBean] getY: " + y);
        return y;
    }

    public void setY(Double y) {
        System.out.println("[AttemptBean] setY: " + y);
        this.y = y;
    }

    public Double getR() {
        System.out.println("[AttemptBean] getR: " + r);
        return r;
    }

    public void setR(Double r) {
        System.out.println("[AttemptBean] setR: " + r);
        this.r = r;
    }

    public void reset() {
        System.out.println("[AttemptBean] reset");
        this.x = null;
        this.y = null;
        this.r = 2.0;
    }

    public Double getGraphX() {
        return graphX;
    }

    public void setGraphX(Double graphX) {
        this.graphX = graphX;
    }

    public Double getGraphY() {
        return graphY;
    }

    public void setGraphY(Double graphY) {
        this.graphY = graphY;
    }

    public List<Attempt> getAttemptList() {
        return attemptList;
    }

    public void setAttemptList(List<Attempt> attemptList) {
        this.attemptList = attemptList;
    }
}