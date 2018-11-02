package game.tictactoe;

import game.ui.UI;
import main.Logger;

public class TicTacToe {
	private Space[][] gameState = new Space[3][3];
	private Space turn = Space.X;
	private Player player1 = null;
	private Player player2 = null;
	private UI ui = null;
	private boolean gameHasEnded = false;
	private Integer[] lastMove = new Integer[2];
	
	public TicTacToe(Player player1, Player player2, UI ui){
		
		for (int i = 0; i < 3; i++) {
			Space[] column = {Space.EMPTY, Space.EMPTY, Space.EMPTY};
			gameState[i] = column;
		}
		
		player1.setParent(this);
		player2.setParent(this);
		
		this.player1 = player1;
		this.player2 = player2;
		this.ui = ui;
		
		player1.makeMove();

	}
	
	public void makeMove(int x, int y) {

		
		if (!gameHasEnded) {		
			if (gameState[x][y] == Space.EMPTY) {
				
				lastMove[0] = x;
				lastMove[1] = y;
				Logger.log(String.format("%s moved to %s, %s", turn, x, y));

				gameState[x][y] = turn;

				updateUI();
				
				
			}
		
		}
		
		if (getWon() != null) {
			ui.setWinText(String.format("%s won the game", getWon()));
			ui.setTurnText("");
			gameHasEnded = true;
			
		}
		
		else if (isCatscratch()) {
			ui.setWinText("Cat Scratch");
			ui.setTurnText("");
			gameHasEnded = true;
		}
		
		else {
			if (turn == Space.X) {
				turn = Space.O;
				player2.makeMove();
			}
			
			else {
				turn = Space.X;
				player1.makeMove();

			}
			ui.setTurnText(String.format("%s's turn", (turn)));
		}
		
	}
	
	private void updateUI() {
		ui.updateGameState(gameState);
	}
	
	public Space getWon() {
		//horizontal checks
		for (int x = 0; x< 3; x++) {
			if (gameState[x][0] == gameState[x][1] && gameState[x][1] == gameState[x][2] && gameState[x][0] != Space.EMPTY) {
				return gameState[x][0];
			}
			
		}
		
		//vertical checks
		for (int y = 0; y< 3; y++) {
			if (gameState[0][y] == gameState[1][y] && gameState[1][y] == gameState[2][y] && gameState[0][y] != Space.EMPTY) {
				return gameState[0][y];
			}
			
		}
		
		
		//diagonal checks
		if (gameState[1][1] != Space.EMPTY) {
			
			if (gameState[0][0] == gameState[1][1] && gameState[2][2] == gameState[1][1]) {
				return gameState[1][1];
			}
			
			else if (gameState[2][0] == gameState[1][1] && gameState[1][1] == gameState[0][2]) {
				return gameState[1][1];
			}
			
		}
		
		return null;
	}
	
	public boolean isCatscratch() {
		
		for (int i = 0; i < 9; i++) {
			if (gameState[i%3][i/3] == Space.EMPTY) {
				return false;
			}
		}
		
		return true;
	}
	
	
	public UI getUI() {
		return ui;
	}
	
	public Space[][] getGameState(){
		return gameState;
	}
	
	public Integer[] getLastMove() {
		return lastMove;
	}
	
}
