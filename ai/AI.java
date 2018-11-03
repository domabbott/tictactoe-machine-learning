package ai;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import game.tictactoe.Space;
import game.tictactoe.TicTacToe;
import main.Logger;

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
	private Boolean storeObject = false;
	private String saveFileName = null;
	
	
	public AI(TicTacToe ttt) {
		this.ttt = ttt;
		currMove.setChildren(getDefaultMoves());
				
	}
	
	public AI(TicTacToe ttt, String saveFileName) {
		this.ttt = ttt;
		this.saveFileName = "/saves/" + saveFileName;
		
		try {
			startFromFile(this.saveFileName);
		}
		
		catch(IOException e) {
			Logger.log("Game save not found, creating new save file");
		}
		
		catch (ClassNotFoundException f) {
			f.printStackTrace();
		}
		
		storeObject = true;
	}
	
	public void startFromFile(String filename) throws IOException, ClassNotFoundException {
		 FileInputStream file = new FileInputStream(filename); 
         ObjectInputStream in = new ObjectInputStream(file); 
           
         currMove = (Move) in.readObject(); 
           
         in.close(); 
         file.close(); 
	}
	
	public void serializeMoveTree() throws IOException {
        FileOutputStream file = new FileOutputStream(saveFileName); 
        ObjectOutputStream out = new ObjectOutputStream(file); 
          
        out.writeObject(currMove); 
          
        out.close(); 
        file.close(); 
          
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
		if (currMove == null) {
			for (Space[] i : ttt.getGameState()) {
				System.out.println(Arrays.toString(i));
			}
		}
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
	
	public void setRewards(Boolean hasWon) {
		float increment;
		
		if (hasWon == null) {
			increment = -10;
		}
		
		else if (hasWon) {
			increment = 100;
		}
		
		else {
			increment = -100;
		}
		
		while (currMove.getParent() != null) {
			float reward = (currMove.getReward() + increment);
			increment *= .7f;
			currMove.setReward(reward);
			currMove = currMove.getParent();
		}
		
		if (storeObject) {
			try {
				serializeMoveTree();
			}
			
			catch (IOException e) {
				e.printStackTrace();
			}
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
		
		
		for (int i = 0; i < weights.length; i++) {
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
