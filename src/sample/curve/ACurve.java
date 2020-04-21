package sample.curve;

import sample.interfaces.ICurve;
import sample.interfaces.IPoint;

public abstract class ACurve implements ICurve {
    protected IPoint a;
    protected IPoint b;

    public ACurve(IPoint a, IPoint b) {
        this.a = a;
        this.b = b;
    }
}
