import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class PolygonButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private MMAdapter mouseMoveListener;
  private UndoManager undoManager;
  private Polygon polygon;
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
    private Point lastPoint;
    private Point firstPoint;
    private PolygonCommand polygonCommand;
    private boolean debounce = false;
    public void mouseClicked(MouseEvent event) {
        // System.out.println("MOUSE CLICKED");
        if (event.getButton() == MouseEvent.BUTTON3) { //Right click
            polygon.removeLast();
            pointCount = 0;
            polygon.addLine(new Line(firstPoint, lastPoint));
            lastPoint = View.mapPoint(event.getPoint());
            drawingPanel.removeMouseListener(this);
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            undoManager.endCommand(polygonCommand);
            debounce = false;
        } else {
            if (++pointCount == 1) {  //Initial click
                polygon = new Polygon();
                lastPoint = View.mapPoint(event.getPoint());
                firstPoint = lastPoint;
                polygonCommand = new PolygonCommand(polygon);
                undoManager.beginCommand(polygonCommand);
                lastPoint = View.mapPoint(event.getPoint());
            } else if (++pointCount >= 2) {
                polygon.addLine(new Line(lastPoint, View.mapPoint(event.getPoint())));
                lastPoint = View.mapPoint(event.getPoint());
                drawingPanel.repaint();  //Needed to show the line
            }
        }
    }
    public Point getLastPoint() {
        return lastPoint;
    }
  }
  private class MMAdapter extends MouseMotionAdapter {
    public void mouseMoved(MouseEvent event) {
        if (mouseHandler.pointCount >= 1) {
            Line line = new Line(mouseHandler.getLastPoint(), View.mapPoint(event.getPoint()));
            if (mouseHandler.debounce == true) {
                polygon.removeLast();
            }
            polygon.addLine(line);
            drawingPanel.repaint();
            mouseHandler.debounce = true;
        }
    }
  }
}