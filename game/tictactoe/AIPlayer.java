package game.tictactoe;

import ai.AI;
import main.Logger;

public class AIPlayer implements Player{

	private TicTacToe ttt = null;
	private AI ai = null;
	private String filename = null;
	
	public AIPlayer(){}
	
	public AIPlayer(String filename) {
		this.filename = filename;
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
	
		if (ttt.getWon() != getPlayer() && ttt.getWon() != null) {
			Logger.log("Setting rewards for ai loss");
			ai.setRewards(false);
		}
		
		else if (ttt.isCatscratch()) {
			Logger.log("Setting rewards for catscratch");
			ai.setRewards(null);
		}
		
		else {
			Integer[] move = ai.getMove();
			ttt.makeMove(move[0], move[1]);
			
		}
		
		
		if (ttt.getWon() == getPlayer()) {
			Logger.log("Setting rewards for ai win");
			ai.setRewards(true);
			
		}
		
	}
	
	
	private Space getPlayer() {
		if (this.equals(ttt.getPlayer1())) {
			return Space.X;
		}
		
		else {
			return Space.O;
		}
	}


}
