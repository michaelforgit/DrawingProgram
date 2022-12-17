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


    items = new Vector<Item>();
    boxes = new Vector<Canvas>();
    Enumeration<Item> e = Command.model.getItems();

    while (e.hasMoreElements()) {
        Item item = e.nextElement();
        items.add(item);
        Canvas box = new Canvas();
        box.setSize(15, 15);
        box.setLocation(item.getCenter().x+item.distance()/2, item.getCenter().y-item.distance()/2);
        box.setBackground(Color.BLACK);
        box.addMouseListener(mouseHandler);
        box.addMouseMotionListener(mouseMoveListener);
        drawingPanel.add(box);
        boxes.add(box);
    }
  }
  private class MouseHandler extends MouseAdapter {
    private MoveCommand moveCommand;
    public void mouseReleased(MouseEvent event) {
        if (moveCommand != null) {
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            // undoManager.beginCommand(moveCommand);
            moveCommand.move(event.getXOnScreen(), event.getYOnScreen());
            undoManager.endCommand(moveCommand);
            boxes.clear();
            drawingPanel.removeAll();
        }
    }
    public void mousePressed(MouseEvent event) {
        if (event.getComponent().getClass() == Canvas.class) {
            Item item = items.get(boxes.indexOf(event.getComponent()));
            int x = event.getXOnScreen();
            int y = event.getYOnScreen();
            
            moveCommand = new MoveCommand(item, x, y);
            undoManager.beginCommand(moveCommand);
        }
    }

  }
  private class MMAdapter extends MouseMotionAdapter {
    public void mouseDragged(MouseEvent event) {
        if (event.getComponent().getClass() == Canvas.class) {
          Item item = items.get(boxes.indexOf(event.getComponent()));
          event.getComponent().setLocation(item.getCenter().x+item.distance()/2, item.getCenter().y-item.distance()/2);
          // System.out.println(event.getXOnScreen() + " " + event.getYOnScreen());
          mouseHandler.moveCommand.move(event.getXOnScreen(), event.getYOnScreen());
          drawingPanel.repaint();
        }
    }
  }
}