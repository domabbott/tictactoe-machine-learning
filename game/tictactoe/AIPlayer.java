package game.tictactoe;

import ai.AI;
import main.Logger;

public class AIPlayer implements Player{

	private TicTacToe ttt = null;
	private AI ai = null;
	private String filename = null;
	private String name = "AI";
	
	public AIPlayer(){}
	
	public AIPlayer(String name) {
		this.name = name;
	}
	
	public AIPlayer(String filename, String name) {
		this.filename = filename;
		
		if (name != null) {
			this.name = name;
		}
	}

	@Override
	public void notifyWin(Boolean hasWon) {
		String state = null;
		
		if (hasWon == null) {
			state = "tied";
		}
		
		else if (hasWon) {
			state = "won";
		}
		
		else {
			state="lost";
		}
		
		Logger.log(String.format("%s has %s", name, state));
		ai.setRewards(hasWon);
	}
	
	@Override
	public void setParent(TicTacToe ttt) {
		this.ttt = ttt;
		
		if (filename == null) {
			ai = new AI(ttt);
		}
		
		else {
			ai = new AI(ttt, filename);
		}
		
	}

	@Override
	public void makeMove() {
		Logger.log(String.format("%s is making a move", name));
		
		Integer[] move = ai.getMove();
		ttt.makeMove(move[0], move[1]);
		
		
	}

}
