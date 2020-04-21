package sample;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;
import sample.custom_context.AContext;
import sample.custom_context.ContextArrow;
import sample.custom_context.ContextSquare;
import sample.interfaces.IContext;
import sample.visual.AVisualCurve;
import sample.visual.VisualBezier;
import sample.visual.VisualLine;

import java.io.*;


public class Controller {
    Stage stage;

    AVisualCurve curveLine;
    AVisualCurve curveBezier;

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Button saveBtn1;

//    @FXML
//    private ToggleGroup generateGroup;

    @FXML
    private JFXToggleButton toggleBtn;

    @FXML
    private Button saveBtn2;

    @FXML
    private Button clearBtn;

    @FXML
    private Button generateBtn;


    void saveSvgDocumentToFile(SVGDocument document, File file) throws IOException {
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
        try (Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
            svgGenerator.stream(document.getDocumentElement(), out);
        }
    }

    void pathTODoc(AContext context, Document document, String svgNS9, Element svgRootD) {
        String hexColor = context.getHEXColor();
        context.getPane().getChildren().forEach( elem -> {
            if (elem instanceof SVGPath) {
                SVGPath tmp = ((SVGPath) elem);

                final Element element = document.createElementNS(svgNS9, "path");
                element.setAttribute("d", tmp.getContent());
                element.setAttribute("stroke-width", ""+tmp.getStrokeWidth());
                element.setAttribute("fill", hexColor);
                element.setAttribute("stroke", "TRANSPARENT");
                if (tmp.getStroke().isOpaque()) {
                    element.setAttribute("stroke", hexColor);
                }
                if (!tmp.getStrokeDashArray().isEmpty()) {
                    element.setAttribute("stroke-dasharray", tmp.getStrokeDashArray().toArray()[1] + ", " + tmp.getStrokeDashArray().toArray()[0]);
                }
                svgRootD.appendChild(element);
            }
        });
    }

    void saveToSVG(IContext context) {
        final DOMImplementation impl9 = SVGDOMImplementation.getDOMImplementation();
        final String svgNS9 = SVGDOMImplementation.SVG_NAMESPACE_URI;
        Document document = impl9.createDocument(svgNS9, "svg", null);
        Element svgRootD = document.getDocumentElement();

        svgRootD.setAttribute("width", "339");
        svgRootD.setAttribute("height", "339");

        pathTODoc((AContext)context, document, svgNS9, svgRootD);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("svg files (*.svg)", "*.svg");

        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                saveSvgDocumentToFile((SVGDocument)document, file);
            } catch (IOException ex) {
                System.out.println("Error");
            }
        }
    }

    @FXML
    void toggleCurveOption(ActionEvent event) {
    }

    @FXML
    void initialize() {
        toggleBtn.setSelected(false);

        IContext contextArrow = new ContextArrow(pane1);
        IContext contextSquare = new ContextSquare(pane2);

        curveLine = new VisualLine(contextArrow);
        curveBezier = new VisualBezier(contextArrow);


        generateBtn.setOnMouseClicked( e -> {
            curveLine.setContext(contextArrow);
            curveLine.draw();
            curveLine.setContext(contextSquare);
            curveLine.draw();

            curveBezier.setContext(contextArrow);
            curveBezier.draw();
            curveBezier.setContext(contextSquare);
            curveBezier.draw();
        });

        clearBtn.setOnAction(e -> {
            pane1.getChildren().clear();
            pane2.getChildren().clear();
        });

        saveBtn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveToSVG(contextArrow);
            }
        });
        saveBtn2.setOnAction( event -> {
            saveToSVG(contextSquare);
        });
    }
}



