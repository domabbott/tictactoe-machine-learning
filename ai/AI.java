package ai;

import java.awt.IllegalComponentStateException;
import java.io.FileInputStream;
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
	
	public Move currMove = new Move();
	private TicTacToe ttt = null;
	private Boolean storeObject = false;
	private String saveFileName = null;
	private int lastAmount = 0;
	
	
	public AI(TicTacToe ttt) {
		this.ttt = ttt;
		populateNewChildren();
				
	}
	
	public AI(TicTacToe ttt, String saveFileName) {
		this.ttt = ttt;
		this.saveFileName = "src/saves/" + saveFileName;
		
		try {
			startFromFile(this.saveFileName);
			storeObject = true;
			populateNewChildren();
		}
		
		catch(IOException e) {
			Logger.log("Game save not found, will create new save file");
			storeObject = true;
			populateNewChildren();
		}
		
		catch (ClassNotFoundException f) {
			f.printStackTrace();
		}
		
	}
	
	public void startFromFile(String filename) throws IOException, ClassNotFoundException {
		 Logger.log("Loading game from " + filename);
		 FileInputStream file = new FileInputStream(filename); 
         ObjectInputStream in = new ObjectInputStream(file); 
           
         currMove = (Move) in.readObject(); 
           
         in.close(); 
         file.close(); 
	}
	
	public void serializeMoveTree() throws IOException {
		Logger.log("Saving game to " + saveFileName);
        FileOutputStream file = new FileOutputStream(saveFileName); 
        ObjectOutputStream out = new ObjectOutputStream(file); 
          
        out.writeObject(currMove); 
          
        out.close(); 
        file.close(); 
          
	}
	
	public Integer[] getMove() {
		logAction();
				
		if (ttt.getLastMove()[0] != null) {
			
			boolean hasMoved = false;
			
			for (int i = 0; i < currMove.getChildren().size(); i++)	{
				Integer[] childCoords = currMove.getChildren().get(i).getCoords();
				Integer[] lastMoveCoords = ttt.getLastMove();
				if (childCoords[0].equals(lastMoveCoords[0]) && childCoords[1].equals(lastMoveCoords[1])) {
					currMove = currMove.getChildren().get(i);
					populateNewChildren();
					hasMoved = true;
					break;
				}
			}
			
			if (!hasMoved) {
				throw new IllegalStateException("Opponent's move wasn't detected");
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
		
		Logger.log("Setting rewards");
		while (currMove.getParent() != null) {
			
			float reward = (currMove.getReward() + increment);
			increment *= -.7f;
			
			//minimum value for a move
			if (reward < 20) {
				reward = 20;
			}
			
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
	
	private void logAction() {
		List<Integer[]> moves = new ArrayList<Integer[]>();
		List<Float> weights = new ArrayList<Float>();
		for (Move i : currMove.getChildren()) {
			moves.add(i.getCoords());
			weights.add(i.getReward());
		}
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < moves.size(); i++) {
			builder.append(String.format("Move: %s, Weight: %s \n", Arrays.toString((moves.get(i))), weights.get(i)));
		}
		
		Logger.log(builder.toString());
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
