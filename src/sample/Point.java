package sample;

import sample.interfaces.IPoint;

public class Point implements IPoint {
    private double x;
    private double y;

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public void setX(double val) {
        this.x = val;
    }

    @Override
    public void setY(double val) {
        this.y = val;
    }
}
