import java.awt.*;
import java.lang.Math;
public class Line extends Item {
  private Point point1;
  private Point point2;
  public Line(Point point1, Point point2) {
    this.point1 = point1;
    this.point2 = point2;
  }
  public Line(Point point1) {
    this.point1 = point1;
	point2 = null;
  }
  public Line() {
	  point1 = null;
	  point2 = null;
  }
  public boolean includes(Point point) {
    return ((distance(point, point1 ) < 10.0) || (distance(point, point2)< 10.0));
  }
  public void render() {
    uiContext.drawLine(point1, point2);
  }
  public void setPoint1(Point point) {
    point1 = point;
  }
  public void setPoint2(Point point) {
    point2 = point;
  }
  public Point getPoint1() {
    return point1;
  }
  public Point getPoint2() {
    return point2;
  }
  public String toString() {
    return "Line  from " + point1 + " to " + point2;
  }
  public Point getCenter() {
    return new Point((getPoint1().x + getPoint2().x)/2, (getPoint1().y + getPoint2().y)/2);
  }
  public int distance() {
    return (int) distance(point1, point2);
  }

  public void move(int x, int y) {
    point1.x = point1.x - x;
    point1.y = point1.y - y;
    point2.x = point2.x - x;
    point2.y = point2.y - y;
    // System.out.println(toString());
  }
}

