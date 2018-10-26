package game.ui;

import game.tictactoe.Space;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

	public void startGame(Stage stage) {
		stage.setTitle("TicTacToe");
		int size = 500;
		
		stage.setHeight(size);
		stage.setWidth(size);
		
		AnchorPane pane = new AnchorPane();
		pane.setPrefHeight(size);
		pane.setPrefWidth(size);
		
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				int[] pos = {x*(size/3) + 40, y*(size/3) + 30};
				Button button = new Button();
				
				button.setLayoutX(pos[0]);
				button.setLayoutY(pos[1]);
				
				int imgSize = size/7;
				button.setPrefSize(imgSize, imgSize);
				
				Image blank = new Image("/resources/empty.png");
				BackgroundImage backgroundImage = new BackgroundImage(blank, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
				Background background = new Background(backgroundImage);
				
				button.setBackground(background);
				
				button.setUserData(x + y*3);
				button.setOnAction(clickFunc);				
				
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
		this.clickFunc = clickFunc;
	}


}
