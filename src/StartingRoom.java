public class StartingRoom extends Room {

    public StartingRoom(int x, int y) {
        super(x, y);
    }

    public String[] returnMapPortion() {
        String[] map = {
                "   ",
                " S ",
                "   "
        };
        return map;
    }
}
