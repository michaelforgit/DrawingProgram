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

}
