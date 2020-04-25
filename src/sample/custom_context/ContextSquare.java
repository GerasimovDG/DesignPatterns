package sample.custom_context;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import sample.interfaces.IPoint;

public class ContextSquare extends AContext {
    public ContextSquare(Pane pane) {
        super(pane);
        hexColor = "#000000";
        svgRule = new SVGPath();
        svgRule.setStrokeWidth(3);
        svgRule.getStrokeDashArray().addAll(3.0, 6.0);
        svgRule.setStroke(Color.web(hexColor));
        svgRule.setFill(Color.web(hexColor));
    }

    private void drawSquarePoint(IPoint point) {
        double width = svgRule.getStrokeWidth();
        SVGPath tmp = new SVGPath();
        tmp.setFill(svgRule.getFill());
        tmp.setStroke(Color.TRANSPARENT);

        String strContext = String.format("M%d,%d h%d v%d h%d L%d,%d", (int)(point.getX()-width), (int)(point.getY()-width),
                (int)width*2, (int)width*2, -(int)width*2, (int)(point.getX()-width), (int)(point.getY()-width));
        tmp.setContent(strContext);
        pane.getChildren().add(tmp);
    }

    @Override
    public void drawStartPoint(IPoint p1) {
        drawSquarePoint(p1);
    }

    @Override
    public void drawEndPoint(IPoint endPoint) {
        drawSquarePoint(endPoint);
    }
}
