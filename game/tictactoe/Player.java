package game.tictactoe;

public interface Player {
	public void makeMove();
	public void setParent(TicTacToe ttt);
	public void notifyWin(Boolean hasWon);
}
