import java.util.Arrays;
import java.util.Scanner;

public class Monopoly_Game {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Die die1 = new Die();
        Die die2 = new Die();
        int playerNumber = Integer.parseInt(args[0]);

        String playerNames[] = new String[playerNumber];
        int startingDiceValues[] = new int[playerNumber];

        for (int i = 0; i < playerNumber; i++) {
            System.out.print("Please enter the name of player" + (i + 1) + ":  ");
            playerNames[i] = input.next();
            die1.rollDie();
            die2.rollDie();
            startingDiceValues[i] = die1.getFaceValue() + die2.getFaceValue();

            for (int j = 0; j < i; j++) {
                if (startingDiceValues[i] == startingDiceValues[j]) {
                    die1.rollDie();
                    die2.rollDie();
                    startingDiceValues[i] = die1.getFaceValue() + die2.getFaceValue();
                    j = -1;
                }
            }
        }

        System.out.println("\nDice tournament started.\n");
        for (int i = 0; i < startingDiceValues.length; i++) {
            System.out.println(playerNames[i] + "'s dice value is: " + startingDiceValues[i]);
        }

        int[] sdvClone = startingDiceValues.clone();
        String[] pnClone = playerNames.clone();
        Arrays.sort(startingDiceValues);

        for (int i = 0; i < startingDiceValues.length; i++) {
            for (int j = 0; j < startingDiceValues.length; j++) {
                if (sdvClone[i] == startingDiceValues[j]) {
                    playerNames[playerNames.length - j - 1] = pnClone[i];
                }
            }
        }

        System.out.println("\nDice tournament ended.\nCurrent positions: \n");
        for (int i = 0; i < startingDiceValues.length; i++) {

            switch (i + 1) {
                case 1:
                    System.out.print("1st");
                    break;
                case 2:
                    System.out.print("2nd");
                    break;
                case 3:
                    System.out.print("3rd");
                    break;
                default:
                    System.out.print((i + 1) + "th");
            }
            System.out.println(" player is " + playerNames[i] + " with a dice value of: " + startingDiceValues[startingDiceValues.length - i - 1]);
        }
        System.out.println();
        startingDiceValues = null;
        sdvClone = null;
        pnClone = null;

        Board board = new Board();
        board.generatePlayers(playerNames, Integer.parseInt(args[1]));
        board.play();
    }
}