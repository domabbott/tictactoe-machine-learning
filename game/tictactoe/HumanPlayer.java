package game.tictactoe;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import main.Logger;

public class HumanPlayer implements Player{
	
	TicTacToe ttt = null;

	@Override
	public void makeMove() {
				
		ttt.getUI().setClickFunc(this::handleClick);

	}
	
	@Override
	public void setParent(TicTacToe ttt) {
		this.ttt = ttt;
	}
	
	private void handleClick(ActionEvent e) {
		
		if (ttt == null) {
			Logger.log("Parent has not been set for player");
		}
		
		else {
			Button source = (Button) e.getSource();
			int pos = (int) source.getUserData();
			
			ttt.makeMove(pos % 3, pos/3);
		}
		
	}
	
}
