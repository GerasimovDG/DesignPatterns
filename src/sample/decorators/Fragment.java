package sample.decorators;

import sample.interfaces.ICurve;
import sample.interfaces.IPoint;

public class Fragment extends ACurveDecorator {
    private double tStart;
    private double tFinish;

    public Fragment(ICurve curve) {
        super(curve);
        this.tStart = 0;
        this.tFinish = 1;
    }
    public Fragment(ICurve curve, double tStart, double tFinish) {
        super(curve);
        if (tStart <= tFinish) {
            this.tStart = tStart;
            this.tFinish = tFinish;
        } else {
            this.tStart = tFinish;
            this.tFinish = tStart;
        }
    }

    @Override
    public IPoint getPoint(double t) {
        if (t < tStart) {
            t = tStart;
        } else if (t > tFinish) {
            t = tFinish;
        }
        return curve.getPoint(t);
    }
}
