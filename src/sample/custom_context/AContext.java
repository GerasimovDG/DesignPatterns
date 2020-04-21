package sample.custom_context;

import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import sample.interfaces.IContext;
import sample.interfaces.IPoint;

public abstract class AContext implements IContext {
    protected Pane pane;
    protected SVGPath svgRule;
    protected String hexColor = null;

    public AContext(Pane pane) {
        this.pane = pane;
    }

    public Pane getPane() {
        return pane;
    }
    public String getHEXColor() {
        return hexColor;
    }

    public abstract void drawStartPoint(IPoint p1);
    public abstract void drawEndPoint(IPoint endPoint);

    public void drawLine(IPoint p1, IPoint p2) {
        SVGPath tmp = new SVGPath();
        tmp.setStrokeWidth(svgRule.getStrokeWidth());
        tmp.setStroke(svgRule.getStroke());
        tmp.setFill(svgRule.getFill());
        tmp.getStrokeDashArray().addAll(svgRule.getStrokeDashArray());

        tmp.setContent(String.format("M%d,%d L%d,%d", (int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY()));
        pane.getChildren().add(tmp);
    }
}
