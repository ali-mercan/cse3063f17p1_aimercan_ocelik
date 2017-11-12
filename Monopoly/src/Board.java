public class Board {

    private Player[] players;
    private Square[] squares = new Square[40];

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

    public Player[] getPlayers() {
        return players;
    }

    public Square[] getSquares() {
        return squares;
    }
}
