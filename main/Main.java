package main;

import game.tictactoe.HumanPlayer;
import game.tictactoe.Player;
import game.tictactoe.TicTacToe;
import game.ui.UI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	static UI ui = new UI();

	public static void main(String[] args) {
		Player player1 = new HumanPlayer();
		Player player2 = new HumanPlayer();
		
		TicTacToe ttt = new TicTacToe(player1, player2, ui);
		
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		
		ui.startGame(stage);
		
	}

}
