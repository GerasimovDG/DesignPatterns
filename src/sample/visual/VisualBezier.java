package sample.visual;

import sample.curve.Bezier;
import sample.Point;
import sample.interfaces.IContext;
import sample.interfaces.ICurve;
import sample.interfaces.IPoint;


public class VisualBezier extends AVisualCurve {
    private IPoint point1 = null;
    private IPoint point2 = null;
    private IPoint point3 = null;
    private IPoint point4 = null;


    public VisualBezier(IContext context) {
        super(context);
    }
    public VisualBezier(IContext context, IPoint p1, IPoint p2, IPoint p3, IPoint p4) {
        super(context);
        this.point1 = new Point(p1.getX(),p1.getY());
        this.point2 = new Point(p2.getX(),p2.getY());
        this.point3 = new Point(p3.getX(),p3.getY());
        this.point4 = new Point(p4.getX(),p4.getY());

        curve = new Bezier(this.point1, this.point2, this.point3, this.point4);
    }
    public VisualBezier(IContext context, ICurve curve) {
        super(context);
        this.point1 = new Point(curve.getPoint(0));
        this.point4 = new Point(curve.getPoint(1));

        this.curve = curve;
    }

//    private void destroyAllPoints() {
//        point1 = null;
//        point2 = null;
//        point3 = null;
//        point4 = null;
//    }
//
//   @Override
//    public void draw() {
//        //if (point1 != null && point4 != null) {
//            //context.drawStartPoint(point1);
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
//            //context.drawEndPoint(point4);
//            context.drawEndPoint(getPoint(1));
//
//            //destroyAllPoints();
//        //}
//    }
}
