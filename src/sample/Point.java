package sample;

import sample.interfaces.IPoint;

public class Point implements IPoint {
    private double x;
    private double y;

    public Point() {}
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point(IPoint point) {
        this.x = point.getX();
        this.y = point.getY();
    }

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

    @Override
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
