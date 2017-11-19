import java.io.*;
import java.util.Scanner;

public class Board {

    private Player[] players;
    private Square[] squares = new Square[40];
    private Die die1 = new Die();
    private Die die2 = new Die();
    private Scanner x;

    public Board() {
        try {
            x = new Scanner(new File("C:\\Users\\oguzc\\IdeaProjects\\Monopoly\\src\\Monopoly-Lots.txt"));
        } catch (Exception e) {
            System.out.println("Monopoly-Lots.txt can not found.");
        }

        while (x.hasNext()) {
            String[] temp = x.nextLine().split(",");
            int a = Integer.parseInt(temp[0]);
            squares[a - 1] = new Lot_Square(a - 1, "Square " + a, Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        }
        for (int i = 0; i < 40; i++) {
            if (squares[i] != null) {
                continue;
            }
            switch (i) {
                case 0:
                    squares[i] = new Go_Square(i, "Go Square");
                    break;
                case 4:
                    squares[i] = new Income_Tax_Square(i, "Income Tax Square");
                    break;
                case 5:
                    squares[i] = new Railroad_Square(i, "Rail Road 1", 200);
                    break;
                case 10:
                    squares[i] = new Jail_Square(i, "Jail Square");
                    break;
                case 12:
                    squares[i] = new Utility_Square(i, "Electric Utility", 150);
                    break;
                case 15:
                    squares[i] = new Railroad_Square(i, "Rail Road 2", 200);
                    break;
                case 20:
                    squares[i] = new Free_Parking_Square(i, "Free Parking Square");
                    break;
                case 25:
                    squares[i] = new Railroad_Square(i, "Rail Road 3", 200);
                    break;
                case 28:
                    squares[i] = new Utility_Square(i, "Water Utility", 150);
                    break;
                case 30:
                    squares[i] = new Go_To_Jail_Square(i, "Go To Jail Square");
                    break;
                case 35:
                    squares[i] = new Railroad_Square(i, "Rail Road 4", 200);
                    break;
                case 38:
                    squares[i] = new Luxury_Tax_Square(i, "Luxury Tax Square");
                    break;
                default:
                    squares[i] = new Square(i, "Square " + (i + 1));
            }
        }
    }


    public void play() {

        int turnNumber = 0;
        boolean play = true;

        while (play) {

            for (int i = 0; i < players.length; i++) {

                System.out.println(players[i].getName() + "'s turn. \nTurn number: " + (turnNumber + 1));

                switch (i + 1) {
                    case 1:
                        System.out.println(players[i].getName() + " is " + (i + 1) + "st player.");
                        break;
                    case 2:
                        System.out.println(players[i].getName() + " is " + (i + 1) + "nd player.");
                        break;
                    case 3:
                        System.out.println(players[i].getName() + " is " + (i + 1) + "rd player.");
                        break;
                    default:
                        System.out.println(players[i].getName() + " is " + (i + 1) + "th player.");
                }

                System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                System.out.println(players[i].getName() + " is currently at " + squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");


                if (!players[i].isInJail()) {

                    die1.rollDie();
                    die2.rollDie();

                    System.out.println(players[i].getName() + " rolled the dice.");
                    System.out.println("Dice show: " + die1.getFaceValue() + " and " + die2.getFaceValue());
                    System.out.println("Total value of dice : " + (die1.getFaceValue() + die2.getFaceValue()));
                    System.out.println(players[i].getName() + " moved " + (die1.getFaceValue() + die2.getFaceValue()) + " squares.");
                    players[i].setCurrentSquareNumber(squares[(players[i].getCurrentSquareNumber() + die1.getFaceValue() + die2.getFaceValue()) % 40]
                            .getSquareNumber());
                    System.out.println(players[i].getName() + " landed in " + squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");

                    switch (squares[players[i].getCurrentSquareNumber()].getClass().getName()) {
                        case "Go_Square":
                            players[i].setMoney(200);
                            System.out.println(players[i].getName() + " earned $200.");
                            System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                            break;
                        case "Income_Tax_Square":
                            System.out.println(players[i].getName() + " paid $" + (int) players[i].getMoney() / 10 + " as Income Tax.");
                            players[i].setMoney(-(players[i].getMoney() / 10));
                            System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                            break;
                        case "Go_To_Jail_Square":
                            players[i].setInJail(true);
                            players[i].setCurrentSquareNumber(10);
                            System.out.println(players[i].getName() + " is send to Jail Square.");
                            System.out.println(players[i].getName() + " is now at Jail Square.");
                            break;
                        case "Luxury_Tax_Square":
                            System.out.println(players[i].getName() + " paid $75 as Luxury Tax.");
                            players[i].setMoney(-75);
                            System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                            break;
                        case "Lot_Square":
                            if (squares[players[i].getCurrentSquareNumber()].getOwnerNumber() == -1) {

                                if (players[i].getMoney() > squares[players[i].getCurrentSquareNumber()].getPrice()) {

                                    die1.rollDie();

                                    System.out.println(players[i].getName() + " rolled a die to buy the square.");
                                    System.out.println("Die shows " + die1.getFaceValue() + ".");

                                    if (die1.getFaceValue() > 4) {
                                        players[i].setMoney(-squares[players[i].getCurrentSquareNumber()].getPrice());
                                        squares[players[i].getCurrentSquareNumber()].setOwnerNumber(i);
                                        System.out.println(players[i].getName() + " bought the " + squares[players[i].getCurrentSquareNumber()]
                                                .getSquareName() + ".");
                                        System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                                    }
                                } else {
                                    System.out.println(players[i].getName() + " does not have enough money to buy " +
                                            squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");
                                }
                            } else {
                                players[i].setMoney(-squares[players[i].getCurrentSquareNumber()].getRent());
                                players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].
                                        setMoney(squares[players[i].getCurrentSquareNumber()].getRent());
                                System.out.println(players[i].getName() + " paid $" + squares[players[i].getCurrentSquareNumber()].getRent() +
                                        " as rent to " + players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getName() + ".");
                                System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                            }
                            break;
                        case "Railroad_Square":
                            if (squares[players[i].getCurrentSquareNumber()].getOwnerNumber() == -1) {

                                if (players[i].getMoney() > squares[players[i].getCurrentSquareNumber()].getPrice()) {

                                    die1.rollDie();

                                    System.out.println(players[i].getName() + " rolled a die to buy the square.");
                                    System.out.println("Die shows " + die1.getFaceValue() + ".");

                                    if (die1.getFaceValue() > 4) {
                                        players[i].setMoney(-squares[players[i].getCurrentSquareNumber()].getPrice());
                                        squares[players[i].getCurrentSquareNumber()].setOwnerNumber(i);
                                        System.out.println(players[i].getName() + " bought the " + squares[players[i].getCurrentSquareNumber()]
                                                .getSquareName() + ".");
                                        System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                                    }
                                } else {
                                    System.out.println(players[i].getName() + " does not have enough money to buy " +
                                            squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");
                                }
                            } else {
                                die1.rollDie();
                                System.out.println(players[i].getName() + " rolled a die to buy the square.");
                                System.out.println("Die shows " + die1.getFaceValue() + ".");
                                players[i].setMoney(-(die1.getFaceValue() * 5) + 25);
                                players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].setMoney((die1.getFaceValue() * 5) + 25);
                                System.out.println(players[i].getName() + " paid $" + (die1.getFaceValue() * 5) + 25 +
                                        " as rent to " + players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getName() + ".");
                                System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");

                            }
                            break;
                        case "Utility_Square":
                            if (squares[players[i].getCurrentSquareNumber()].getOwnerNumber() == -1) {

                                if (players[i].getMoney() > squares[players[i].getCurrentSquareNumber()].getPrice()) {

                                    die1.rollDie();

                                    System.out.println(players[i].getName() + " rolled a die.");
                                    System.out.println("Die shows " + die1.getFaceValue() + ".");

                                    if (die1.getFaceValue() > 4) {
                                        players[i].setMoney(-squares[players[i].getCurrentSquareNumber()].getPrice());
                                        squares[players[i].getCurrentSquareNumber()].setOwnerNumber(i);
                                        System.out.println(players[i].getName() + " bought the " + squares[players[i].getCurrentSquareNumber()]
                                                .getSquareName() + ".");
                                        System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                                    }
                                } else {
                                    System.out.println(players[i].getName() + " does not have enough money to buy " +
                                            squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");
                                }
                            } else {
                                die1.rollDie();
                                System.out.println(players[i].getName() + " rolled a die.");
                                System.out.println("Die shows " + die1.getFaceValue() + ".");
                                players[i].setMoney(-die1.getFaceValue() * 10);
                                players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].setMoney(die1.getFaceValue() * 10);
                                System.out.println(players[i].getName() + " paid $" + die1.getFaceValue() * 10 +
                                        " as rent to " + players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getName() + ".");
                                System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                            }
                            break;
                    }
                } else {
                    if (players[i].getMoney() > 50) {
                        players[i].setMoney(-50);
                        players[i].setInJail(false);
                        System.out.println(players[i].getName() + " paid $50 to get out of the jail.");
                        System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                    }
                }
                if (players[i].getMoney() <= 0) {
                    System.out.println(players[i].getName() + " went bankrupt and removed from the game.");
                    play = false;
                    break;
                }
                System.out.println();

            }
            turnNumber++;
        }
    }

    public void generatePlayers(String[] playerNames, int startingMoney) {
        players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++) {
            players[i] = new Player(playerNames[i], startingMoney);
        }
    }
}
