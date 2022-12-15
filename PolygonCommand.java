public class PolygonCommand extends Command {
    private Polygon polygon;
    public PolygonCommand(Polygon polygon) {
        this.polygon = polygon;
    }
    public void execute() {
        model.addItem(polygon);
    }
    public boolean undo() {
        model.removeItem(polygon);
        return true;
    }
    public boolean redo() {
        execute();
        return true;
    }
}
