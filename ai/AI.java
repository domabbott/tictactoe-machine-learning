package ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import game.tictactoe.Space;
import game.tictactoe.TicTacToe;

public class AI {
	/*
	 * Steps:
	 * 	-Make a move according to a tree, random if nessesary
	 *  -Store in an array
	 * 	-At the end, depending on whether result is win or lose, assign point values to posotions seen along the way
	 * 	-Store the result in a tree
	 * 
	 */
	
	private Move currMove = new Move();
	
	
	private TicTacToe ttt = null;
	
	
	public AI(TicTacToe ttt) {
		this.ttt = ttt;
		currMove.setChildren(getDefaultMoves());
				
	}
	
	public Integer[] getMove() {
		
	
		if (ttt.getLastMove()[0] != null) {
			for (int i = 0; i < currMove.getChildren().size(); i++)	{
				Integer[] childCoords = currMove.getChildren().get(i).getCoords();
				Integer[] lastMoveCoords = ttt.getLastMove();
				if (childCoords[0] == lastMoveCoords[0] && childCoords[1] == lastMoveCoords[1]) {
					currMove = currMove.getChildren().get(i);
					populateNewChildren();
					break;
				}
			}
	
		}
		
		currMove = selectWeighted();
		populateNewChildren();
		return currMove.getCoords();
	}
	
	private void populateNewChildren() {
		if (currMove.getChildren() == null) {
			currMove.setChildren(getDefaultMoves());

		}
	}
	
	private List<Move> getDefaultMoves(){
		List<Move> moveList = new ArrayList<Move>();
		
		for (Integer[] i : enumMoves()) {
			Move move = new Move();
			move.setParent(currMove);
			move.setCoords(i);				
			moveList.add(move);
		}
		
		return moveList;
		
	}
	
	public void setRewards(boolean hasWon) {
		float reward;
		
		if (hasWon) {
			reward = 100;
		}
		
		else {
			reward = -100;
		}
		
		while (currMove.getParent() != null) {
			currMove.setReward(reward);
			reward += (reward * .9);
		}
	}
	
	private Move selectWeighted() {
		float[] weights = new float[currMove.getChildren().size()];
		float[] cumulativeWeights = new float[weights.length];
		float total = 0;
		
		for (int i = 0; i < weights.length; i++) {
			weights[i] = currMove.getChildren().get(i).getReward();
			total += weights[i];
			cumulativeWeights[i] = total; 
		}
		
		float rand = new Random().nextFloat() * total;
		
		
		for (int i = 1; i < weights.length; i++) {
			if (rand <= cumulativeWeights[i]) {
				return  currMove.getChildren().get(i);
			}
		}

		return null;
		
	}
	
	private List<Integer[]> enumMoves() {
		
		List<Integer[]> moves = new ArrayList<Integer[]>();
		
		for (int i = 0; i < 9; i++) {
			if (ttt.getGameState()[i/3][i%3] == Space.EMPTY) {
				Integer[] move = {i/3, i%3};
				moves.add(move);
			}
		}
		
		return moves;
	}
	
	
}
