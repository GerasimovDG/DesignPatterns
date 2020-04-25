package sample;

import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
import sample.curve.Bezier;
import sample.curve.Line;
import sample.custom_context.AContext;
import sample.custom_context.ContextArrow;
import sample.custom_context.ContextSquare;
import sample.decorators.Fragment;
import sample.decorators.MoveTo;
import sample.interfaces.IContext;
import sample.interfaces.ICurve;
import sample.interfaces.IPoint;
import sample.visual.AVisualCurve;
import sample.visual.VisualBezier;
import sample.visual.VisualLine;

import java.io.*;
import java.util.ArrayList;

public class Controller {
    Stage stage;

    AVisualCurve curveLine;
    AVisualCurve curveBezier;

    ArrayList<AVisualCurve> listVisualCurves1 = new ArrayList<>();
    ArrayList<AVisualCurve> listVisualCurves2 = new ArrayList<>();

    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;

    @FXML
    private Button saveBtn1;
    @FXML
    private Button saveBtn2;

    @FXML
    private ListView<String> listView;
    @FXML
    private ListView<String> listView2;

    @FXML
    private JFXToggleButton toggleBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button setFragmentBtn;
    @FXML
    private TextField fStartField;
    @FXML
    private TextField fFinishField;
    @FXML
    private Button combineBtn;

    @FXML
    private Button setFragmentBtn2;
    @FXML
    private TextField fStartField2;
    @FXML
    private TextField fFinishField2;
    @FXML
    private Button combineBtn2;


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



    private void generateLine(MouseEvent e, IContext context, IPoint p1, IPoint p2,  int countPaneClick,
                              ListView<String> listView, ArrayList<AVisualCurve> listVisualCurves){
        if (countPaneClick == 0) {
            p1.setXY(e.getX(), e.getY());
        } else if (countPaneClick == 1) {
            p2.setXY(e.getX(), e.getY());

            curveLine = new VisualLine(context, new Line(p1,p2));
            listView.getItems().add(String.format("Line (%3.0f,%3.0f - %3.0f,%3.0f)", p1.getX(), p1.getY(), p2.getX(), p2.getY()));
            listVisualCurves.add(curveLine);
            curveLine.draw();
        }
    }
    private void generateBezier(MouseEvent e, IContext context, IPoint p1, IPoint p2, IPoint p3, IPoint p4, int countPaneClick, ListView<String> listView, ArrayList<AVisualCurve> listVisualCurves1) {
        if (countPaneClick == 0) {
            p1.setXY(e.getX(), e.getY());
        } else if (countPaneClick == 1) {
            p2.setXY(e.getX(), e.getY());
        } else if (countPaneClick == 2) {
            p3.setXY(e.getX(), e.getY());
        } else if (countPaneClick == 3) {
            p4.setXY(e.getX(), e.getY());

            curveBezier = new VisualBezier(context, new Bezier(p1,p2,p3,p4));
            listView.getItems().add(String.format("Bezier (%3.0f,%3.0f - %3.0f,%3.0f)", p1.getX(), p1.getY(), p4.getX(), p4.getY()));
            listVisualCurves1.add(curveBezier);
            curveBezier.draw();
        }
    }

    private int countPaneClick = 0;
    void drawOnPane1(MouseEvent e, IContext context, IPoint p1, IPoint p2, IPoint p3, IPoint p4) {
        int toggle = toggleBtn.isSelected() ? 1 : 0;
        switch (toggle) {
            case 0:
                generateLine(e, context, p1, p2, countPaneClick, listView, listVisualCurves1);
                countPaneClick = (countPaneClick + 1) % 2;
                break;
            case 1:
                generateBezier(e, context, p1, p2, p3, p4, countPaneClick, listView, listVisualCurves1);
                countPaneClick = (countPaneClick + 1) % 4;
                break;
        }
    }

    private int countPaneClick2 = 0;
    void drawOnPane2(MouseEvent e, IContext context, IPoint p1, IPoint p2, IPoint p3, IPoint p4) {
        int toggle = toggleBtn.isSelected() ? 1 : 0;
        switch (toggle) {
            case 0:
                generateLine(e, context, p1, p2, countPaneClick2, listView2, listVisualCurves2);
                countPaneClick2 = (countPaneClick2 + 1) % 2;
                break;
            case 1:
                generateBezier(e, context, p1, p2, p3, p4, countPaneClick2, listView2, listVisualCurves2);
                countPaneClick2 = (countPaneClick2 + 1) % 4;
                break;
        }
    }


    @FXML
    void initialize() {
        toggleBtn.setSelected(false);
        fStartField.setText("0");
        fFinishField.setText("1");
        fStartField2.setText("0");
        fFinishField2.setText("1");
        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        langsSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        MultipleSelectionModel<String> langsSelectionModel2 = listView2.getSelectionModel();
        langsSelectionModel2.setSelectionMode(SelectionMode.MULTIPLE);

        IContext contextArrow = new ContextArrow(pane1);
        IContext contextSquare = new ContextSquare(pane2);

        IPoint p1 = new Point();
        IPoint p2 = new Point();
        IPoint p3 = new Point();
        IPoint p4 = new Point();

        // перемещение выбранной кривой по двойному клину
        pane1.setOnMouseClicked( mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    // перемещение кривой при двойном клике
                    moveCurveOnPane(mouseEvent,contextArrow, pane1, listView, listVisualCurves1);
                    countPaneClick = 0;
                } else if (mouseEvent.getClickCount() == 1) {
                    // рисование кривой
                    drawOnPane1(mouseEvent, contextArrow, p1 ,p2 ,p3 ,p4);
                }
            }
        });

        pane2.setOnMouseClicked( mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    // перемещение кривой при двойном клике
                    moveCurveOnPane(mouseEvent,contextSquare, pane2, listView2, listVisualCurves2);
                    countPaneClick2 = 0;
                } else if (mouseEvent.getClickCount() == 1) {
                    // рисование кривой
                    drawOnPane2(mouseEvent, contextSquare, p1 ,p2 ,p3 ,p4);
                }
            }
        });

        // очищение полей
        clearBtn.setOnAction(e -> {
            pane1.getChildren().clear();
            pane2.getChildren().clear();

            listView.getItems().clear();
            listView2.getItems().clear();
            listVisualCurves1.clear();
            listVisualCurves2.clear();
        });

        saveBtn1.setOnAction( event -> saveToSVG(contextArrow));
        saveBtn2.setOnAction( event -> saveToSVG(contextSquare));

        // //удаление кривой
        // onViewBtnClick(listBtn, listView, listVisualCurves1, pane1);
        // onViewBtnClick(listBtn2, listView2, listVisualCurves2, pane2);

        // выделение фрагмента
        onFragmentBtnClick(setFragmentBtn, contextArrow, listView, listVisualCurves1, pane1, fStartField, fFinishField);
        onFragmentBtnClick(setFragmentBtn2, contextSquare, listView2, listVisualCurves2, pane2, fStartField2, fFinishField2);

        // совмещение двух кривых
        combineCurves(langsSelectionModel, contextArrow, combineBtn, listView, listVisualCurves1, pane1);
        combineCurves(langsSelectionModel2, contextSquare, combineBtn2, listView2, listVisualCurves2, pane2);
    }

    // совмещение двух кривых
    private void combineCurves(MultipleSelectionModel<String> langsSelectionModel2, IContext contextSquare, Button combineBtn2, ListView<String> listView2, ArrayList<AVisualCurve> listVisualCurves2, Pane pane2) {
        combineBtn2.setOnMouseClicked(e -> {
            ObservableList<String> selected = langsSelectionModel2.getSelectedItems();
            String[] idxStr = new String[2]; // содержание двух выбранных строк в ListView
            int idx1 = -1;
            int idx2 = -1;
            for (int i = 0; i< 2; i++) {
                idxStr[i] = selected.get(i);
            }
            int size = listView2.getItems().size();
            for (int i = 0; i < size; i++) {
                if(listView2.getItems().get(i).equalsIgnoreCase(idxStr[0])) {
                    idx1 = i;
                }
                if(listView2.getItems().get(i).equalsIgnoreCase(idxStr[1])) {
                    idx2 = i;
                    break;
                }
            }
            if (idx1 >= 0 && idx2 >= 0 && idx1 != idx2) {
                ICurve curve1 = listVisualCurves2.get(idx1).getCurve();
                ICurve curve2 = listVisualCurves2.get(idx2).getCurve();

                int idxLine = listView2.getItems().get(idx2).indexOf("Line");
                int idxBezier = listView2.getItems().get(idx2).indexOf("Bezier");
                listView2.getItems().remove(idx2);
                listVisualCurves2.remove(idx2);

                IPoint p = new Point(curve1.getPoint(1));

                if (idxLine != -1) {
                    listVisualCurves2.add(new VisualLine(contextSquare, new MoveTo(curve2, p)));
                    listView2.getItems().add(String.format("CombineLine in (%3.0f,%3.0f)", p.getX(), p.getY()));
                } else if (idxBezier != -1) {
                    listVisualCurves2.add(new VisualBezier(contextSquare, new MoveTo(curve2, p)));
                    listView2.getItems().add(String.format("CombineBezier in (%3.0f,%3.0f)", p.getX(), p.getY()));
                }

                pane2.getChildren().clear();
                for (AVisualCurve curve : listVisualCurves2) {
                    curve.draw();
                }
            }
        });
    }

    // выделение фрагмента
    private void onFragmentBtnClick(Button setFragmentBtn, IContext context, ListView<String> list,
                                    ArrayList<AVisualCurve> listCurves, Pane pane, TextField fStartField, TextField fFinishField ) {
        if (!setFragmentBtn.isDisabled()) {
            setFragmentBtn.setOnMouseClicked(e -> {
                double fStart = Double.parseDouble(fStartField.getText());
                double fFinish = Double.parseDouble(fFinishField.getText());

                if (fStart >= 0 && fStart < 1 && fFinish > 0 && fFinish <= 1) {
                    if (!list.getItems().isEmpty()) {
                        int idx = list.getSelectionModel().getSelectedIndex();

                        if (idx >= 0) {
                            ICurve curve = listCurves.get(idx).getCurve();
                            int idxLine = list.getItems().get(idx).indexOf("Line");
                            int idxBezier = list.getItems().get(idx).indexOf("Bezier");

                            if (idxLine != -1) {
                                listCurves.add(new VisualLine(context, new Fragment(curve, fStart, fFinish)));
                                list.getItems().add(String.format("FragmentLine in (%3.0f,%3.0f)", curve.getPoint(fStart).getX(), curve.getPoint(fStart).getY()));
                            } else if (idxBezier != -1) {
                                listCurves.add(new VisualBezier(context, new Fragment(curve, fStart, fFinish)));
                                list.getItems().add(String.format("FragmentBezier in (%3.0f,%3.0f)", curve.getPoint(fStart).getX(), curve.getPoint(fStart).getY()));
                            }

                            pane.getChildren().clear();
                            for (AVisualCurve elem : listCurves) {
                                elem.draw();
                            }
                        }
                    }
                }
            });
        }
    }

    // перемещение кривой (при двойном клике на поле)
    private void moveCurveOnPane(MouseEvent mouseEvent, IContext context, Pane pane,
                                 ListView<String> listView, ArrayList<AVisualCurve> listVisualCurves) {
        if (!listView.getItems().isEmpty()) {
            int idx = listView.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                ICurve curv = listVisualCurves.get(idx).getCurve();
                IPoint p = new Point(mouseEvent.getX(),mouseEvent.getY());

                int idxLine = listView.getItems().get(idx).indexOf("Line");
                int idxBezier = listView.getItems().get(idx).indexOf("Bezier");
                listView.getItems().remove(idx);
                listVisualCurves.remove(idx);

                if (idxLine != -1) {
                    listVisualCurves.add(new VisualLine(context, new MoveTo(curv, p)));
                    listView.getItems().add(String.format("MoveLine in (%3.0f,%3.0f)", p.getX(), p.getY()));
                } else if (idxBezier != -1) {
                    listVisualCurves.add(new VisualBezier(context, new MoveTo(curv, p)));
                    listView.getItems().add(String.format("MoveBezier in (%3.0f,%3.0f)", p.getX(), p.getY()));
                }

                pane.getChildren().clear();
                for (AVisualCurve curve : listVisualCurves) {
                    curve.draw();
                }
            }
        }
    }

    // удаление кривой (сейчас не используется)
    private void onViewBtnClick(Button listBtn, ListView<String> list, ArrayList<AVisualCurve> listCurves, Pane pane) {
        if (!listBtn.isDisabled()) {
            listBtn.setOnMouseClicked(e -> {
                if (!list.getItems().isEmpty()) {
                    int idx = list.getSelectionModel().getSelectedIndex();
                    if (idx >= 0) {
                        list.getItems().remove(idx);
                        listCurves.remove(idx);

                        pane.getChildren().clear();
                        for (AVisualCurve curve : listCurves) {
                            curve.draw();
                        }
                    }
                }
            });
        }
    }
}



