import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class LineButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private MMAdapter mouseMoveListener;
  private LineCommand lineCommand;
  private UndoManager undoManager;
  public LineButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Line");
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
    private boolean debounce = false;
    public void mouseClicked(MouseEvent event) {
    if (++pointCount == 1) {
        lineCommand = new LineCommand(View.mapPoint(event.getPoint()));
        undoManager.beginCommand(lineCommand);
        firstPoint = View.mapPoint(event.getPoint());
        debounce = true;
    } else if (pointCount == 2) {
        debounce = false;
        pointCount = 0;
        lineCommand.setLinePoint(View.mapPoint(event.getPoint()));
        drawingPanel.removeMouseListener(this);
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        undoManager.undo();  // Remove the temp line that was drawn
        undoManager.endCommand(lineCommand);
      }
    }
    public Point getFirstPoint() {
      return firstPoint;
    }
  }
  private class MMAdapter extends MouseMotionAdapter {
    public void mouseMoved(MouseEvent event) {
      if (mouseHandler.pointCount == 1) {
        if (mouseHandler.debounce == false) {
          undoManager.undo();
        }
        mouseHandler.debounce = false;
        System.out.println("Should be drawing a line.");
        lineCommand = new LineCommand(mouseHandler.getFirstPoint(), View.mapPoint(event.getPoint()));
        undoManager.beginCommand(lineCommand);
        drawingPanel.repaint();
      }
    }
  }
}