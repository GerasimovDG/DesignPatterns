package sample.visual;

import sample.curve.Line;
import sample.Point;
import sample.interfaces.IContext;
import sample.interfaces.IPoint;

public class VisualLine extends AVisualCurve {
    public VisualLine(IContext context) {
        super(context);
    }

    @Override
    public void draw() {
        IPoint p1 = new Point();
        IPoint p2 = new Point();

        p1.setX(10 + (int) (Math.random() * 320));
        p1.setY(10 + (int) (Math.random() * 320));
        context.drawStartPoint(p1);

        p2.setX(10 + (int) (Math.random() * 320));
        p2.setY(10 + (int) (Math.random() * 320));

        curve = new Line(p1, p2);
        IPoint firstPoint = getPoint(0);
        IPoint secondPoint = getPoint(1);
        context.drawLine(firstPoint, secondPoint);
        context.drawEndPoint(secondPoint);
    }
}
