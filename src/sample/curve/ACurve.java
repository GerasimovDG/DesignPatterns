package sample.curve;

import sample.Point;
import sample.interfaces.ICurve;
import sample.interfaces.IPoint;

public abstract class ACurve implements ICurve {
    private IPoint a;
    private IPoint b;

    public ACurve(IPoint a, IPoint b) {
        this.a = new Point(a);
        this.b = new Point(b);
    }
    public ACurve(ICurve curve) {
        this.a = new Point(curve.getPoint(0));
        this.b = new Point(curve.getPoint(1));
    }

    IPoint getPointA() {
        return a;
    }
    IPoint getPointB() {
        return b;
    }
}
