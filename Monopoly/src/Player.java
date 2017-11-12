public class Player {

    private String name;
    private int money=200;
    private int currentSquareNumber=0;
    private boolean inJail = false;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCurrentSquareNumber() {
        return currentSquareNumber;
    }

    public void setCurrentSquareNumber(int currentSquareNumber) {
        this.currentSquareNumber = currentSquareNumber;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money += money;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }
}
