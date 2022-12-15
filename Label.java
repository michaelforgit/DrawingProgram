import java.awt.*;
public class Label extends Item {
  private Point startingPoint;
  private String text = "";
  public Label(Point point) {
    startingPoint = point;
  }
  public void addCharacter(char character) {
    text += character;
  }
  public void removeCharacter() {
    if (text.length() > 0) {
      text = text.substring(0, text.length() - 1);
    }
  }
  public boolean includes(Point point) {
    return distance(point, startingPoint) < 10.0;
  }
 public void render() {
   uiContext.drawLabel(text, startingPoint);
  }
  public String getText() {
    return text;
  }
  public Point getStartingPoint() {
    return startingPoint;
  }
  public Point getCenter() {
    return startingPoint;
  }
  public void move(int x, int y) {
    startingPoint.x -= x;
    startingPoint.y -= y;
  }
  public int distance() {
    int distance = 0;
    for (int i = 0; i < text.length(); i++) {
      distance += 10;
    }
    return distance;
  }
}
