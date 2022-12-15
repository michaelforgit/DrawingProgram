import java.awt.*;

public class MoveCommand extends Command {
    private Item item;
    private int x;
    private int y;
    int index;

    public MoveCommand(Item item, int x, int y, Canvas box) {
        this.item = item;
        this.x = x;
        this.y = y;
    }

    public void execute() {

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
        x = this.x - x;
        y = this.y - y;
        System.out.println(">>>");
        System.out.println(x + " " + y);

        item.move(x/10,y/10);
    }
    // public void move() {
    //     item.move(x, y);
    // }
}
