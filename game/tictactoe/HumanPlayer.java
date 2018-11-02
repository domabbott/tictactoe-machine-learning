package game.tictactoe;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class HumanPlayer implements Player{
	
	private TicTacToe ttt = null;

	@Override
	public void makeMove() {
		ttt.getUI().setClickFunc(this::handleClick);
	}
	
	@Override
	public void setParent(TicTacToe ttt) {
		this.ttt = ttt;
	}
	
	public void handleClick(ActionEvent e) {
		
		if (ttt == null) {
			throw new IllegalStateException("Parent has not been set for player");
		}
		
		else {
			Button source = (Button) e.getSource();
			int pos = (int) source.getUserData();
			ttt.makeMove(pos % 3, pos/3);
		}
				
	}
	
}
