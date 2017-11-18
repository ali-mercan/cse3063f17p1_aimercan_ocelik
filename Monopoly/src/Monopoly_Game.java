import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Monopoly_Game {
    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        Die die1 = new Die();
        Die die2 = new Die();
        int playerNumber = Integer.parseInt(args[0]);
     /*   System.out.print("Please enter player number: ");

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
        }*/

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

        System.out.println("\nDice tournament started.\n");
        for(int i=0; i<startingDiceValues.length; i++){
            System.out.println(playerNames[i]+"'s dice value is: "+startingDiceValues[i]);
        }

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

        System.out.println("\nDice tournament ended.\nCurrent positions: \n");
        for(int i=0; i<startingDiceValues.length; i++){
            System.out.println("Player"+(i+1)+" is "+playerNames[i]+" with a dice value of: "+startingDiceValues[startingDiceValues.length-i-1]);
        }
        System.out.println();
        startingDiceValues=null;
        sdvClone=null;
        pnClone=null;
        Board board = new Board();
        board.generatePlayers(playerNames,Integer.parseInt(args[1]));
      board.play();
//       board.print();
    }
}
