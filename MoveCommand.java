import java.util.*;
import java.awt.*;

public class MoveCommand extends Command {
    private Item item;
    private int x;
    private int y;
    private LinkedList<Point> history;
    private int hisPos = 0;
    private boolean moving = false;
    public MoveCommand(Item item, int x, int y) {
        this.item = item;
        this.x = x;
        this.y = y;
        this.history = new LinkedList<Point>();
    }

    public void execute() {
        history.add(hisPos, new Point(this.x, this.y));
        history.add(hisPos+1, new Point(0, 0));
        hisPos = hisPos + 1;
        moving = true;
    }

    public boolean undo() {
        moving = false;
        item.move(this.x - history.get(hisPos-1).x, this.y - history.get(hisPos-1).y);
        hisPos = hisPos - 1;
        model.setChanged();
        return true;
    }

    public boolean redo() {
        moving = false;
        item.move(-history.get(hisPos+1).x, -history.get(hisPos+1).y);
        hisPos = hisPos + 1;

        model.setChanged();
        return true;
    }

    public boolean end() {
        moving = false;
        return true;
    }

    public void move(int x, int y) {
        Point test;
        item.move(this.x-x,this.y-y);
        this.y = y;
        this.x = x;
        if (moving == true) {
            test = new Point(this.x - history.get(hisPos-1).x, this.y - history.get(hisPos-1).y);
            history.set(hisPos, test);
        }


    }
}
