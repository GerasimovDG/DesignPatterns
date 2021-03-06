package sample.curve;

import sample.Point;
import sample.interfaces.IPoint;

public class Bezier extends ACurve {
    private IPoint c;
    private IPoint d;

    public Bezier(IPoint a, IPoint c, IPoint d, IPoint b) {
        super(a, b);
        this.c = new Point(c);
        this.d = new Point(d);
    }

    @Override
    public IPoint getPoint(double t) {
        IPoint res = new Point();
        IPoint a = getPointA();
        IPoint b = getPointB();

        res.setX(Math.pow((1 - t), 3) * a.getX() + 3 * t * (1 - t) * (1 - t) * c.getX() + 3 * t * t * (1 - t) * d.getX() + Math.pow(t, 3) * b.getX());
        res.setY(Math.pow((1 - t), 3) * a.getY() + 3 * t * (1 - t) * (1 - t) * c.getY() + 3 * t * t * (1 - t) * d.getY() + Math.pow(t, 3) * b.getY());
        return res;
    }
}
