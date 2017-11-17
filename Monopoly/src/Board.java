public class Board {

    private Player[] players;
    private Square[] squares = new Square[40];
    private Die die1 = new Die();
    private Die die2 = new Die();

    public Board(String[] playerNames) {
        players = new Player[playerNames.length];
        for(int i=0; i< playerNames.length; i++){
            players[i] = new Player(playerNames[i]);
        }
        for(int i=0; i<40; i++){
            switch (i){
                case 0:
                    squares[i] = new Go_Square(i, "Go Square");
                    break;
                case 4:
                    squares[i] = new Income_Tax_Square(i,"Income Tax Square");
                    break;
                case 10:
                    squares[i] = new Jail_Square(i,"Jail Square");
                    break;
                case 20:
                    squares[i] = new Free_Parking_Square(i,"Free Parking Square");
                    break;
                case 30:
                    squares[i] = new Go_To_Jail_Square(i,"Go To Jail Square");
                    break;
                case 38:
                    squares[i] = new Luxury_Tax_Square(i,"Luxury Tax Square");
                    break;
                default:
                    squares[i] = new Square(i,"Square "+(i+1));
            }
        }
    }

    public void play(int iterationNumber){

        for (int j = 0; j<iterationNumber ; j++) {
            for (int i = 0; i < players.length; i++) {

                if (players[i].getMoney() > 0) {
                    System.out.println(players[i].getName() + "'s turn. \nTurn number: " + (j + 1));
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
                            break;
                    }

                    System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                    System.out.println(players[i].getName() + " is currently at " + squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");
                    if (!players[i].isInJail()) {
                        System.out.println(players[i].getName() + " rolled the dice.");
                        die1.rollDie();
                        die2.rollDie();
                        System.out.println("Dice show: " + die1.getFaceValue() + " and " + die2.getFaceValue());
                        System.out.println("Total value of dice : " + (die1.getFaceValue() + die2.getFaceValue()));
                        System.out.println(players[i].getName() + " moved " + (die1.getFaceValue() + die2.getFaceValue()) + " squares.");
                        players[i].setCurrentSquareNumber(squares[(players[i].getCurrentSquareNumber() + die1.getFaceValue() + die2.getFaceValue()) % 40].getSquareNumber());
                        System.out.println(players[i].getName() + " landed in " + squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");
                        switch (players[i].getCurrentSquareNumber()) {
                            case 0:
                                players[i].setMoney(200);
                                System.out.println(players[i].getName() + " earned $200.");
                                System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                                break;
                            case 4:
                                System.out.println(players[i].getName() + " paid $" + (int) players[i].getMoney() / 10 + " as Income Tax.");
                                players[i].setMoney(-(players[i].getMoney() / 10));
                                System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                                break;
                            case 30:
                                players[i].setInJail(true);
                                players[i].setCurrentSquareNumber(10);
                                System.out.println(players[i].getName() + " is send to Jail Sqauare.");
                                System.out.println(players[i].getName() + " is now at Jail Sqauare.");
                                break;
                            case 38:
                                System.out.println(players[i].getName() + " paid $75 as Luxury Tax.");
                                players[i].setMoney(-75);
                                System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                                if (players[i].getMoney() <= 0) {
                                    System.out.println(players[i].getName() + " went bankrupt and removed from the game.");
                                }
                                break;
                        }
                    }
                    else {
                        if (players[i].getMoney() > 50) {
                            players[i].setMoney(-50);
                            players[i].setInJail(false);
                            System.out.println(players[i].getName() + " paid $50 to get out of the jail.");
                            System.out.println(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                        }
                    }
                    System.out.println();
                }
            }
        }
    }
}
