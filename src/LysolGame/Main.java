package LysolGame;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	} 

	public void start(Stage stage){
		GameMenuStage theMenuStage = new GameMenuStage();
		theMenuStage.setStage(stage);
	}

}
