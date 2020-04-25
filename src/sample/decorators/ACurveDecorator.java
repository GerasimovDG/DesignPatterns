package sample.decorators;

import sample.interfaces.ICurve;
import sample.interfaces.IPoint;

public abstract class ACurveDecorator implements ICurve {
    protected ICurve curve;

    public ACurveDecorator(ICurve curve) {
        this.curve = curve;
    }

    public void setComponent(ICurve curve) {
        this.curve = curve;
    }

    public abstract IPoint getPoint(double t);
}
