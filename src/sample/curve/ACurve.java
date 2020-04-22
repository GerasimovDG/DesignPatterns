package sample.curve;

import sample.interfaces.ICurve;
import sample.interfaces.IPoint;

public abstract class ACurve implements ICurve {
    private IPoint a;
    private IPoint b;

    public ACurve(IPoint a, IPoint b) {
        this.a = a;
        this.b = b;
    }

    IPoint getPointA() {
        return a;
    }
    IPoint getPointB() {
        return b;
    }
}
