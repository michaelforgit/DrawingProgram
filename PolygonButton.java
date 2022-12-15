import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class PolygonButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private MMAdapter mouseMoveListener;
  private LineCommand lineCommand;
  private UndoManager undoManager;
  public PolygonButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Polygon");
    this.undoManager = undoManager;
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    mouseHandler = new MouseHandler();
    mouseMoveListener = new MMAdapter();
  }
  public void actionPerformed(ActionEvent event) {
    view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    // Change cursor when button is clicked
    drawingPanel.addMouseListener(mouseHandler);
  // Start listening for mouseclicks on the drawing panel
    drawingPanel.addMouseMotionListener(mouseMoveListener);
  }
  private class MouseHandler extends MouseAdapter {
    private int pointCount = 0;
    private Point firstPoint;
    private Point lastPoint;
    private PolygonCommand polygonCommand;
    private Polygon polygon;
    // private boolean debounce = false;
    public void mouseClicked(MouseEvent event) {
        System.out.println("MOUSE CLICKED");
        if (event.getButton() == MouseEvent.BUTTON3) { //Right click
            pointCount = 0;
            polygon.addLine(new Line(firstPoint, lastPoint));
            lastPoint = View.mapPoint(event.getPoint());
            drawingPanel.removeMouseListener(this);
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            undoManager.endCommand(polygonCommand);
        } else {
            if (++pointCount == 1) {
                polygon = new Polygon();
                lastPoint = View.mapPoint(event.getPoint());
                firstPoint = lastPoint;
                polygon.addLine(new Line(lastPoint, View.mapPoint(event.getPoint())));
                polygonCommand = new PolygonCommand(polygon);
                undoManager.beginCommand(polygonCommand);
                lastPoint = View.mapPoint(event.getPoint());
            } else if (++pointCount >= 2) {
                polygon.addLine(new Line(lastPoint, View.mapPoint(event.getPoint())));
                lastPoint = View.mapPoint(event.getPoint());
                drawingPanel.repaint();
            }
        }
    }
    public Point getFirstPoint() {
      return firstPoint;
    }
  }
  private class MMAdapter extends MouseMotionAdapter {
    // public void mouseMoved(MouseEvent event) {
    //   if (mouseHandler.pointCount == 1) {
    //     if (mouseHandler.debounce == false) {
    //       undoManager.undo();
    //     }
    //     mouseHandler.debounce = false;
    //     lineCommand = new LineCommand(mouseHandler.getFirstPoint(), View.mapPoint(event.getPoint()));
    //     undoManager.beginCommand(lineCommand);
    //     drawingPanel.repaint();
    //   }
    // }
  }
}