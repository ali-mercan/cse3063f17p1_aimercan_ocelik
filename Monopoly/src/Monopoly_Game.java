import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Monopoly_Game {
    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        Die die1 = new Die();
        Die die2 = new Die();
        int playerNumber;
        int iterationNumber;
        System.out.print("Please enter player number: ");

        while (true) {
            try {
                playerNumber = input.nextInt();
                if(playerNumber >= 2 && playerNumber <= 8){
                    break;
                }else {
                    System.out.print("Invalid input. Player number should be minimum 2 and maximum 8. Please reenter player number: ");
                    input.nextLine();
                }
            }
            catch (InputMismatchException e) {
                System.out.print("Invalid input. Please reenter player number: ");
                input.nextLine();
            }
        }

        String playerNames[] = new String[playerNumber];
        int startingDiceValues[] = new int[playerNumber];

        for(int i=0; i<playerNumber; i++){
            System.out.print("Please enter the name of player"+(i+1)+":  ");
            playerNames[i] = input.next();
            die1.rollDie();
            die2.rollDie();
            startingDiceValues[i] = die1.getFaceValue() + die2.getFaceValue();

            for(int j=0; j<i; j++){
                if(startingDiceValues[i]==startingDiceValues[j]){
                    die1.rollDie();
                    die2.rollDie();
                    startingDiceValues[i] = die1.getFaceValue() + die2.getFaceValue();
                    j=0;
                }
            }
        }

        System.out.print("Please enter iteration number: ");
        while (true) {
            try {
                iterationNumber = input.nextInt();
                break;
            }
            catch (InputMismatchException e) {
                System.out.print("Invalid input. Please reenter iteration number: ");
                input.nextLine();
            }
        }

        System.out.println("\nDice tournament started.\n");
        for(int i=0; i<startingDiceValues.length; i++){
            System.out.println(playerNames[i]+"'s dice value is: "+startingDiceValues[i]);
        }

        System.out.println("\nDice tournament ended.\nCurrent positions: \n");
        int[] sdvClone =  startingDiceValues.clone();
        String[] pnClone = playerNames.clone();
        Arrays.sort(startingDiceValues);

        for(int i=0; i<startingDiceValues.length; i++){
            for(int j=0; j<startingDiceValues.length; j++){
                if(sdvClone[i]==startingDiceValues[j]){
                    playerNames[playerNames.length-j-1]=pnClone[i];
                }
            }
        }

        for(int i=0; i<startingDiceValues.length; i++){
            System.out.println("Player"+(i+1)+" is "+playerNames[i]+" with a dice value of: "+startingDiceValues[startingDiceValues.length-i-1]);
        }
        System.out.println();
        startingDiceValues=null;
        sdvClone=null;
        pnClone=null;

        Board board = new Board(playerNames);

        for (int j = 0; j<iterationNumber ; j++) {
            for (int i = 0; i < playerNumber; i++) {

                if (board.getPlayers()[i].getMoney() > 0) {
                    System.out.println(board.getPlayers()[i].getName() + "'s turn. \nTurn number: " + (j + 1));
                    switch (i + 1) {
                        case 1:
                            System.out.println(board.getPlayers()[i].getName() + " is " + (i + 1) + "st player.");
                            break;
                        case 2:
                            System.out.println(board.getPlayers()[i].getName() + " is " + (i + 1) + "nd player.");
                            break;
                        case 3:
                            System.out.println(board.getPlayers()[i].getName() + " is " + (i + 1) + "rd player.");
                            break;
                        default:
                            System.out.println(board.getPlayers()[i].getName() + " is " + (i + 1) + "th player.");
                            break;
                    }

                    System.out.println(board.getPlayers()[i].getName() + "'s total cash is $" + board.getPlayers()[i].getMoney() + ".");
                    System.out.println(board.getPlayers()[i].getName() + " is currently at " + board.getSquares()[board.getPlayers()[i].getCurrentSquareNumber()].getSquareName() + ".");
                    if (!board.getPlayers()[i].isInJail()) {
                        System.out.println(board.getPlayers()[i].getName() + " rolled the dice.");
                        die1.rollDie();
                        die2.rollDie();
                        System.out.println("Dice show: " + die1.getFaceValue() + " and " + die2.getFaceValue());
                        System.out.println("Total value of dice : " + (die1.getFaceValue() + die2.getFaceValue()));
                        System.out.println(board.getPlayers()[i].getName() + " moved " + (die1.getFaceValue() + die2.getFaceValue()) + " squares.");
                        board.getPlayers()[i].setCurrentSquareNumber(board.getSquares()[(board.getPlayers()[i].getCurrentSquareNumber() + die1.getFaceValue() + die2.getFaceValue()) % 40].getSquareNumber());
                        System.out.println(board.getPlayers()[i].getName() + " landed in " + board.getSquares()[board.getPlayers()[i].getCurrentSquareNumber()].getSquareName() + ".");
                        switch (board.getPlayers()[i].getCurrentSquareNumber()) {
                            case 0:
                                board.getPlayers()[i].setMoney(200);
                                System.out.println(board.getPlayers()[i].getName() + " earned $200.");
                                System.out.println(board.getPlayers()[i].getName() + "'s total cash is $" + board.getPlayers()[i].getMoney() + ".");
                                break;
                            case 4:
                                System.out.println(board.getPlayers()[i].getName() + " paid $" + (int) board.getPlayers()[i].getMoney() / 10 + " as Income Tax.");
                                board.getPlayers()[i].setMoney(-(board.getPlayers()[i].getMoney() / 10));
                                System.out.println(board.getPlayers()[i].getName() + "'s total cash is $" + board.getPlayers()[i].getMoney() + ".");
                                break;
                            case 30:
                                board.getPlayers()[i].setInJail(true);
                                board.getPlayers()[i].setCurrentSquareNumber(10);
                                break;
                            case 38:
                                System.out.println(board.getPlayers()[i].getName() + " paid $75 as Luxury Tax.");
                                board.getPlayers()[i].setMoney(-75);
                                System.out.println(board.getPlayers()[i].getName() + "'s total cash is $" + board.getPlayers()[i].getMoney() + ".");
                                if (board.getPlayers()[i].getMoney() <= 0) {
                                    System.out.println(board.getPlayers()[i].getName() + " went bankrupt and removed from the game.");
                                }
                                break;
                        }
                    }
                    else {
                        if (board.getPlayers()[i].getMoney() > 50) {
                            board.getPlayers()[i].setMoney(-50);
                            board.getPlayers()[i].setInJail(false);
                            System.out.println(board.getPlayers()[i].getName() + " paid $50 to get out of the jail.");
                            System.out.println(board.getPlayers()[i].getName() + "'s total cash is $" + board.getPlayers()[i].getMoney() + ".");
                        }
                    }
                    System.out.println();
                }
            }
        }
    }
}
