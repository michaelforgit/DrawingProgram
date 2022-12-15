import java.awt.*;

public class MoveCommand extends Command {
    private Item item;
    private int x;
    private int y;
    private Canvas box;
    private Point location;
    int index;

    public MoveCommand(Item item, int x, int y, Canvas box) {
        this.item = item;
        this.x = x;
        this.y = y;
        this.box = box;
    }

    public void execute() {
        // box.setLocation(location);
        // Point origLocation = item.getCenter();
        // int xOffset = (int)(location.getX() - origLocation.getX());
        // int yOffset = (int)(location.getY() - origLocation.getY());
        item.move(x, y);

    }

    public boolean undo() {
        return true;
    }

    public boolean redo() {
        execute();
        return true;
    }

    public boolean end() {
        return true;
    }

    public void move(int x, int y) {
        item.move(x, y);
    }
}
