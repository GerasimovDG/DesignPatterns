package sample.custom_context;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import sample.Point;
import sample.interfaces.IPoint;

import static javafx.scene.paint.Color.*;

public class ContextArrow extends AContext {
    private IPoint previousPoint = new Point();

    public ContextArrow(Pane pane) {
        super(pane);
        hexColor = "#218359";
        svgRule = new SVGPath();
        svgRule.setStrokeWidth(5);
        svgRule.setStroke(Color.web(hexColor));
        svgRule.setFill(Color.web(hexColor));
    }

    private String circlePath(int cx, int cy, int r){
        return "M " +cx+' '+cy+ " m -" +r+ ", 0 a " +r+','+r+ " 0 1,0 " +(r*2)+ ",0 a " +r+','+r+ " 0 1,0 -" +(r*2)+ ",0";
    }

    @Override
    public void drawStartPoint(IPoint p1) {
        SVGPath tmp = new SVGPath();
        tmp.setStrokeWidth(svgRule.getStrokeWidth());
        tmp.setStroke(TRANSPARENT);
        tmp.setFill(svgRule.getFill());
        tmp.getStrokeDashArray().addAll(svgRule.getStrokeDashArray());

        double width = svgRule.getStrokeWidth();
        String circlePath = circlePath((int)p1.getX(), (int)p1.getY(), (int)width*2);

        tmp.setContent(circlePath);
        pane.getChildren().add(tmp);
    }

    @Override
    public void drawEndPoint(IPoint endPoint) {
        double x = previousPoint.getX();
        double y = previousPoint.getY();
        double x1 = endPoint.getX();
        double y1 = endPoint.getY();
        double  beta = Math.atan2(y-y1,x1-x); //{ArcTan2 ищет арктангенс от x/y что бы неопределенностей не
        //  возникало типа деления на ноль}
        double alpha = Math.PI/5;// {угол между основной осью стрелки и рисочки в конце}
        int r1 = 20; //{длинна риски}

        int x2 =(int) Math.round(x1 - r1*Math.cos(beta + alpha));
        int y2 =(int)Math.round(y1 + r1*Math.sin(beta + alpha));

        SVGPath tmp = new SVGPath();
        tmp.setContent(String.format("M%d,%d L%d,%d", (int)x1, (int)y1, x2, y2));
        tmp.setStrokeWidth(svgRule.getStrokeWidth());
        tmp.setStroke(svgRule.getStroke());
        tmp.setFill(svgRule.getFill());
        tmp.getStrokeDashArray().addAll(svgRule.getStrokeDashArray());
        pane.getChildren().add(tmp);

        x2 =(int)Math.round(x1 - r1*Math.cos(beta - alpha));
        y2 =(int)Math.round(y1 + r1*Math.sin(beta -  alpha));

        tmp = new SVGPath();
        tmp.setContent(String.format("M%d,%d L%d,%d", (int)x1, (int)y1, x2, y2));
        tmp.setStrokeWidth(svgRule.getStrokeWidth());
        tmp.setStroke(svgRule.getStroke());
        tmp.setFill(svgRule.getFill());
        tmp.getStrokeDashArray().addAll(svgRule.getStrokeDashArray());
        pane.getChildren().add(tmp);
    }

    public void drawLine(IPoint p1, IPoint p2) {
        super.drawLine(p1,p2);
        previousPoint.setX(p1.getX());
        previousPoint.setY(p1.getY());
    }
}
