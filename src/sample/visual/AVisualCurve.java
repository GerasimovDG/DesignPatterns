package sample.visual;

import sample.interfaces.IContext;
import sample.interfaces.ICurve;
import sample.interfaces.IDrawable;
import sample.interfaces.IPoint;

public abstract class AVisualCurve implements ICurve, IDrawable {
    protected ICurve curve;
    protected final int ACCURACY = 20;

    protected IContext context;

    public AVisualCurve(IContext context) {
        this.context = context;
    }

    public IPoint getPoint(double t) {
        return curve.getPoint(t);
    }

    public void setContext(IContext context) {
        this.context = context;
    }

    boolean isEqualPoints(IPoint p1, IPoint p2) {
        if (p1.getX() == p2.getX()) {
            return p1.getY() == p2.getY();
        }
        return false;
    }

    public ICurve getCurve() {
        return curve;
    }

    @Override
    public void draw() {
        context.drawStartPoint(getPoint(0));

        for (double i = 0; i < ACCURACY; i++) {
            double t = i / ACCURACY;
            IPoint firstPoint = getPoint(t);
            t = (i + 1) / ACCURACY;
            IPoint secondPoint = getPoint(t);

            if (!isEqualPoints(firstPoint, secondPoint)) {
                context.drawLine(firstPoint, secondPoint);
            }
        }
        context.drawEndPoint(getPoint(1));
    }
}
