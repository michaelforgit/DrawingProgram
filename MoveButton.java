import javax.swing.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Vector;
import java.awt.*;

public class MoveButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private MMAdapter mouseMoveListener;
  private UndoManager undoManager;
  private Vector<Item> items;
  private Vector<Canvas> boxes;
  
  public MoveButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Move");
    this.undoManager = undoManager;
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    mouseHandler = new MouseHandler();
    mouseMoveListener = new MMAdapter();
  }
  public void actionPerformed(ActionEvent event) {
    view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    drawingPanel.addMouseListener(mouseHandler);
    drawingPanel.addMouseMotionListener(mouseMoveListener);

    items = new Vector<Item>();
    boxes = new Vector<Canvas>();
    Enumeration<Item> e = Command.model.getItems();

    while (e.hasMoreElements()) {
        Item item = e.nextElement();
        items.add(item);
        Canvas box = new Canvas();
        box.setSize(25, 25);
        box.setLocation(item.getCenter());
        box.setBackground(Color.RED);
        box.addMouseListener(mouseHandler);
        box.addMouseMotionListener(mouseMoveListener);
        drawingPanel.add(box);
        boxes.add(box);
    }
  }
  private class MouseHandler extends MouseAdapter {
    private MoveCommand moveCommand;
    public void mouseClicked(MouseEvent event) {
        System.out.println("MOUSE CLICKED");
    }
    public void mouseReleased(MouseEvent event) {

        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        undoManager.endCommand(moveCommand);
    }
    public void mousePressed(MouseEvent event) {
        if (event.getComponent().getClass() == Canvas.class) {
            Item item = items.get(boxes.indexOf(event.getComponent()));
            Canvas box = (Canvas) event.getComponent();
            int x = event.getX();
            int y = event.getY();
            moveCommand = new MoveCommand(item, x, y, box);
            undoManager.beginCommand(mouseHandler.moveCommand);
        }
    }

  }
  private class MMAdapter extends MouseMotionAdapter {
    public void mouseDragged(MouseEvent event) {
        if (event.getComponent().getClass() == Canvas.class) {
            mouseHandler.moveCommand.move(event.getX(), event.getY());
            drawingPanel.repaint();
        }
    }
  }
}