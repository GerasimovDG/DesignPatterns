package sample.decorators;

import sample.Point;
import sample.interfaces.ICurve;
import sample.interfaces.IPoint;

public class MoveTo extends ACurveDecorator {
    private IPoint p;

    public MoveTo(ICurve curve, IPoint p) {
        super(curve);
        this.p = p;
    }

    @Override
    public IPoint getPoint(double t) {
        IPoint startP = curve.getPoint(0);
        IPoint currentP = curve.getPoint(t);
        IPoint newPoint = new Point();

        newPoint.setX( p.getX() + (currentP.getX() - startP.getX()));
        newPoint.setY( p.getY() + (currentP.getY() - startP.getY()));

        return newPoint;
    }
}
