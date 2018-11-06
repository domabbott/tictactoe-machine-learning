package main;

import game.tictactoe.AIPlayer;
import game.tictactoe.HumanPlayer;
import game.tictactoe.Player;
import game.tictactoe.TicTacToe;
import game.ui.UI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	

	public static void main(String[] args) {
	
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		UI ui = new UI(stage);

		ui.startGame();
		
		Player player0 = new AIPlayer("AI Bob");
		Player player1 = new AIPlayer("AI Bill");
		Player player2 = new HumanPlayer("Dominic");
		Player player3 = new HumanPlayer();
				
		TicTacToe ttt = new TicTacToe(player0, player1, ui);
	}
	

}
