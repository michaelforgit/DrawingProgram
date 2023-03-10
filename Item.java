import java.io.*;
import java.util.Vector;
import java.awt.*;
public abstract class Item implements Serializable {
  protected static UIContext uiContext;
  public static void setUIContext(UIContext uiContext) {
    Item.uiContext = uiContext;
  }
  public abstract boolean includes(Point point);
  public abstract Point getCenter();
  public abstract void move(int x, int y);
  // public abstract void setCenter(Point point);
  protected double distance(Point point1, Point point2) {
    double xDifference = point1.getX() - point2.getX();
    double yDifference = point1.getY() - point2.getY();
    return ((double) (Math.sqrt(xDifference * xDifference + yDifference * yDifference)));
  }
  public abstract int distance();
  public  void render() {
    //uiContext.draw(this);
  }
}
