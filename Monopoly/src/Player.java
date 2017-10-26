public class Player {

    private String name;
    private Money balance;
    private int currentSquare=1;
    private Square estate;
    private boolean inJail;
    private boolean turn;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCurrentSquare() {
        return currentSquare;
    }

    public void setCurrentSquare(int move) {
        currentSquare += move;
        currentSquare %= 40;
    }
}
