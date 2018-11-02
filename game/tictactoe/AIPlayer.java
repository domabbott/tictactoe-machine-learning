package game.tictactoe;

import ai.AI;

public class AIPlayer implements Player{

	TicTacToe ttt = null;
	AI ai = null;
	
	@Override
	public void setParent(TicTacToe ttt) {
		this.ttt = ttt;
		ai = new AI(ttt);
	}

	@Override
	public void makeMove() {
		Integer[] move = ai.getMove();
		ttt.makeMove(move[0], move[1]);
	}


}
