import java.util.Vector;
import java.awt.*;
import java.util.*;

public class Polygon extends Item{
    private Vector<Line> lines;

    public Polygon() {
        lines = new Vector<Line>();
    }

    public boolean includes(Point point) {
        for (Line line : lines) {
            if (line.includes(point)) {
                return true;
            }
        }
        return false;
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public void render() {
        for (Line line : lines) {
            line.render();
        }
    }

    public void removeLast() {
        lines.remove(lines.size() - 1);
    }
    public Point getCenter() {
        int x = 0;
        int y = 0;
        for (Line line : lines) {
            x += line.getCenter().x;
            y += line.getCenter().y;
        }
        return new Point(x/lines.size(), y/lines.size());
    }
    public void move(int x, int y) {
        for (Line line : lines) {
            line.move(x, y);
        }
    }
}
