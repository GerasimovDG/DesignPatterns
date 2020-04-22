package sample.curve;

import sample.Point;
import sample.interfaces.IPoint;

public class Line extends ACurve {
    public Line(IPoint a, IPoint b) {
        super(a, b);
    }

    @Override
    public IPoint getPoint(double t) {
        IPoint res = new Point();
        IPoint a = getPointA();
        IPoint b = getPointB();
        double aX = a.getX();
        double bX = b.getX();
        double aY = a.getY();
        double bY = b.getY();

        res.setX((1 - t) * aX + t * bX);
        res.setY((1 - t) * aY + t * bY);
        return res;
    }
}
