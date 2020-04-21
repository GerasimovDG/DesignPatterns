package sample.visual;

import sample.curve.Bezier;
import sample.Point;
import sample.interfaces.IContext;
import sample.interfaces.IPoint;


public class VisualBezier extends AVisualCurve {
    public VisualBezier(IContext context) {
        super(context);
    }

    @Override
    public void draw() {
        IPoint p1 = new Point();
        IPoint p2 = new Point();
        IPoint p3 = new Point();
        IPoint p4 = new Point();

        p1.setX(10 + (int) (Math.random() * 320));
        p1.setY(10 + (int) (Math.random() * 320));
        context.drawStartPoint(p1);

        p2.setX(10 + (int) (Math.random() * 320));
        p2.setY(10 + (int) (Math.random() * 320));
        p3.setX(10 + (int) (Math.random() * 320));
        p3.setY(10 + (int) (Math.random() * 320));
        p4.setX(10 + (int) (Math.random() * 320));
        p4.setY(10 + (int) (Math.random() * 320));
        curve = new Bezier(p1, p2, p3, p4);
        for (double i = 0; i < ACCURACY; i++) {
            double t = i / ACCURACY;
            IPoint firstPoint = getPoint(t);
            t = (i + 1) / ACCURACY;
            IPoint secondPoint = getPoint(t);

            context.drawLine(firstPoint,secondPoint);
        }
        context.drawEndPoint(p4);
    }
}
