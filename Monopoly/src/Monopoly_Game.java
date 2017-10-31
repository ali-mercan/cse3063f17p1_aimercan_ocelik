import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Monopoly_Game {
    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        Die die1 = new Die();
        Die die2 = new Die();
        int playerNumber;

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
            startingDiceValues[i] = die1.getFaceValue() + die2.getFaceValue();

            for(int j=0; j<i; j++){
                if(startingDiceValues[i]==startingDiceValues[j]){
                    startingDiceValues[i] = die1.getFaceValue() + die2.getFaceValue();
                    j=0;
                }
            }
        }

        for(int i=0; i<startingDiceValues.length; i++){
            System.out.println("Player"+(i+1)+"'s name is: "+playerNames[i]+". Dice value is: "+startingDiceValues[i]);
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

        for(int i=0; i<startingDiceValues.length; i++){
            System.out.println("Player"+(i+1)+"'s name is: "+playerNames[i]+". Dice value is: "+startingDiceValues[startingDiceValues.length-i-1]);
        }

        startingDiceValues=null;
        sdvClone=null;
        pnClone=null;

        Board board = new Board(playerNames);

        for (int j = 0; j<10; j++) {
            for (int i = 0; i < playerNumber; i++) {

                System.out.println(board.getPlayers()[i].getName()+"'s turn. \nTurn number: "+(j+1));
                switch (i+1){
                    case 1:  System.out.println(board.getPlayers()[i].getName()+" is "+(i+1)+"st player."); break;
                    case 2:  System.out.println(board.getPlayers()[i].getName()+" is "+(i+1)+"nd player."); break;
                    case 3:  System.out.println(board.getPlayers()[i].getName()+" is "+(i+1)+"rd player."); break;
                    default: System.out.println(board.getPlayers()[i].getName()+" is "+(i+1)+"th player."); break;
                }

                System.out.println(board.getPlayers()[i].getName()+" is currently at Square "+board.getPlayers()[i].getCurrentSquare()+".");
                System.out.println(board.getPlayers()[i].getName()+" rolled the dice.");
                System.out.println("Dice show: " + die1.getFaceValue() +" and "+die2.getFaceValue());
                board.getPlayers()[i].setCurrentSquare(die1.getFaceValue() + die2.getFaceValue());
                System.out.println(board.getPlayers()[i].getName() +" moved "+ (die1.getFaceValue() + die2.getFaceValue())+" squares.");
                System.out.println(board.getPlayers()[i].getName()+" is currently at Square "+board.getPlayers()[i].getCurrentSquare()+".");
                System.out.println();
            }
        }
    }
}
