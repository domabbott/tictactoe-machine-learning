package game.tictactoe;

import com.sun.javafx.util.Logging;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import main.Logger;

public class HumanPlayer implements Player{
	
	private TicTacToe ttt = null;
	private String name = "Human Player";

	public HumanPlayer() {}
	
	public HumanPlayer(String name) {
		this.name = name;
	}
	
	@Override
	public void makeMove() {
		ttt.getUI().setClickFunc(this::handleClick);
	}
	
	@Override
	public void setParent(TicTacToe ttt) {
		this.ttt = ttt;
	}
	
	public void handleClick(ActionEvent e) {
		Logger.log(String.format("%s is making a move", name));
		
		if (ttt == null) {
			throw new IllegalStateException("Parent has not been set for player");
		}
		
		else {
			Button source = (Button) e.getSource();
			int pos = (int) source.getUserData();
			ttt.makeMove(pos % 3, pos/3);
		}
				
	}

	@Override
	public void notifyGameEnd(Boolean hasWon) {
		String state = null;
		
		if (hasWon) {
			state = "won";
		}
		
		else {
			state = "lost";
		}
		
		Logger.log(String.format("%s has %s", name, state));
		
	}
	
}
