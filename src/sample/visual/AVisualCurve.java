package sample.visual;

import javafx.scene.layout.Pane;
import sample.interfaces.IContext;
import sample.interfaces.ICurve;
import sample.interfaces.IDrawable;
import sample.interfaces.IPoint;

public abstract class AVisualCurve implements ICurve, IDrawable {
    protected ICurve curve;
    protected final int ACCURACY = 10;

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
}
