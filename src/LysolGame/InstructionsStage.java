package LysolGame;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class InstructionsStage {
	private StackPane pane;
	private Scene scene;
	private Stage stage;
	private Canvas canvas;
	private GraphicsContext gc;
	private final Image bg = new Image("imagefiles/instructionsScreen.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);


	//the class constructor
	public InstructionsStage() {
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.gc.drawImage(this.bg, 0, 0);


		//initialize buttons
		Button backbtn = new Button("Back");
		backbtn.setTranslateX(-295);
		backbtn.setTranslateY(-200);
		this.addEventHandler(backbtn);

		//set stage elements here
		pane.getChildren().add(this.canvas);
		pane.getChildren().add(backbtn);

		this.stage = stage;


		this.stage.setTitle("Lysol Disinfecter Instructions");
		this.stage.setScene(this.scene);



		this.stage.show();
	}

	private void addEventHandler(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				GameMenuStage theMenuStage = new GameMenuStage();
				theMenuStage.setStage(stage);


				}
			});
	}
}