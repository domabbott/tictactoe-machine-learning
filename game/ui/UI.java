package game.ui;

import game.tictactoe.HumanPlayer;
import game.tictactoe.Space;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class UI {
	
	private Button[][] spaces = new Button[3][3];
	EventHandler<ActionEvent> clickFunc = null;
	Label winLabel = null;
	Label turnLabel = null;

	public void startGame(Stage stage) {
		stage.setTitle("TicTacToe");
		int size = 600;
		
		stage.setHeight(size);
		stage.setWidth(size);
		
		AnchorPane pane = new AnchorPane();
		pane.setPrefHeight(size);
		pane.setPrefWidth(size);
		pane.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm());
		
		winLabel = new Label();
		winLabel.getStyleClass().add("label");
		winLabel.setLayoutY(size*.8);
		winLabel.setLayoutX(size/4);
		pane.getChildren().add(winLabel);
		
		turnLabel = new Label();
		winLabel.getStyleClass().add("label");
		turnLabel.setLayoutY(size*.01);
		turnLabel.setLayoutX(size/3);
		pane.getChildren().add(turnLabel);
		
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				int[] pos = {x*(size/5) + 140, y*(size/5) + 140};
				Button button = new Button();
				
				button.setLayoutX(pos[0]);
				button.setLayoutY(pos[1]);
				button.setMaxSize(size/10, size/10);
				
				int imgSize = size/7;
				button.setPrefSize(imgSize, imgSize);
				
				Image blank = new Image("/resources/empty.png");
				BackgroundImage backgroundImage = new BackgroundImage(blank, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
				Background background = new Background(backgroundImage);
				
				button.setBackground(background);
				
				button.setUserData(x + y*3);				
				spaces[x][y] = button;

				
				pane.getChildren().add(button);
			}
		}
		
		Image img = new Image("/resources/background.png");
		BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
		Background background = new Background(backgroundImage);
		pane.setBackground(background);
				
		stage.setScene(new Scene(pane));
		stage.setResizable(false);
				
		stage.show();
	}
	
	public void updateGameState(Space[][] gameState) {
		
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				spaces[x][y].setBackground(spaceToBackground(gameState[x][y]));
			}
		}
		
		
		
	}
	
	private Background spaceToBackground(Space space) {
		String imgSrc = "/resources/";
		
		if (space == Space.X) {
			imgSrc += "X.png";
		}
		
		else if (space == Space.O) {
			imgSrc += "O.png";
		}
		
		else {
			imgSrc += "empty.png";
		}
		
		Image img = new Image(imgSrc);
		BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
		return new Background(backgroundImage);
	}
	

	public void setClickFunc(EventHandler<ActionEvent> clickFunc) {
		for (Button[] i : spaces) {
			for (Button j : i) {
				j.setOnAction(clickFunc);
			}
		}

	}
	
	public void setWinText(String text) {
		winLabel.setText(text);
	}
	
	public void setTurnText(String text) {
		turnLabel.setText(text);
	}


}
