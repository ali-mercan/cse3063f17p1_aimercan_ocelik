public class Board {

    private Player[] players;
    private Square[] squares;

    public Board(String[] playerNames) {
        players = new Player[playerNames.length];
        for(int i=0; i< playerNames.length; i++){
            players[i] = new Player(playerNames[i]);
        }
    }

    public Player[] getPlayers() {
        return players;
    }
}
