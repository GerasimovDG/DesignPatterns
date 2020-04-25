package sample.visual;

import sample.curve.Line;
import sample.Point;
import sample.interfaces.IContext;
import sample.interfaces.ICurve;
import sample.interfaces.IPoint;

public class VisualLine extends AVisualCurve {
    private IPoint point1;
    private IPoint point2;

    public VisualLine(IContext context) {
        super(context);
        this.point1 = null;
        this.point2 = null;
    }
    public VisualLine(IContext context, IPoint p1, IPoint p2) {
        super(context);
        this.point1 = new Point(p1.getX(), p1.getY());
        this.point2 = new Point(p2.getX(), p2.getY());

        curve = new Line(this.point1, this.point2);
    }
    public VisualLine(IContext context, ICurve curve) {
        super(context);
        this.point1 = curve.getPoint(0);
        this.point2 = curve.getPoint(1);

        this.curve = new Line(curve);
    }

//    private void destroyAllPoints() {
//        point1 = null;
//        point2 = null;
//    }
//
//    @Override
//    public void draw() {
//        //if (point1 != null && point2 != null) {
//            context.drawStartPoint(getPoint(0));
//
//            for (double i = 0; i < ACCURACY; i++) {
//                double t = i / ACCURACY;
//                IPoint firstPoint = getPoint(t);
//                t = (i + 1) / ACCURACY;
//                IPoint secondPoint = getPoint(t);
//
//                if (!isEqualPoints(firstPoint, secondPoint)) {
//                    context.drawLine(firstPoint, secondPoint);
//                }
//            }
//            context.drawEndPoint(getPoint(1));
//
//            //destroyAllPoints();
//        //}
//
//    }
}
