package game.tictactoe;

import game.ui.UI;
import main.Logger;

public class TicTacToe {
	private Space[][] gameState = new Space[3][3];
	private Space turn = Space.X;
	private Player player1 = null;
	private Player player2 = null;
	private UI ui = null;
	
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
		
		if (getWon() != null) {
			Logger.log(String.format("%s won the game!", spaceToString(getWon())));
		}
		
		else if (isCatscratch()) {
			Logger.log("Catscratch!");
		}
		
		else {
		
			if (gameState[x][y] == Space.EMPTY) {
				gameState[x][y] = turn;
				
				updateUI();
				
				if (turn == Space.X) {
					player2.makeMove();
					turn = Space.O;
				}
				
				else {
					player1.makeMove();
					turn = Space.X;
				}
			}
		}
		
	}
	
	private void updateUI() {
		ui.updateGameState(gameState);
	}
	
	private Space getWon() {
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
				return gameState[0][0];
			}
			
			else if (gameState[2][0] == gameState[1][1] && gameState[1][1] == gameState[0][2]) {
				return gameState[2][0];
			}
			
		}
		
		return null;
	}
	
	private boolean isCatscratch() {
		
		for (int i = 0; i < 9; i++) {
			if (gameState[i%3][i/3] == Space.EMPTY) {
				return false;
			}
		}
		
		return true;
	}
	
	private String spaceToString(Space space) {
		if (space == Space.O) {
			return "O";
		}
		
		else if (space == Space.X) {
			return "X";
		}
		
		else {
			return "Empty";
		}
	}
	
	
	public UI getUI() {
		return ui;
	}
	
}
