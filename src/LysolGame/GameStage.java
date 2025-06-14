package LysolGame;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameStage {
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;
	public final static int Y_MARGIN = 80;
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;



	//the class constructor
	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.gametimer = new GameTimer(this.gc,this.scene, this);
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;


		//set stage elements here
		this.root.getChildren().add(canvas);

		this.stage.setTitle("Lysol Disinfecter Game");
		this.stage.setScene(this.scene);

		//invoke the start method of the animation timer
		this.gametimer.start();

		this.stage.show();
	}

	void flashGameOver(int n){
		this.gametimer.stop();
		PauseTransition transition = new PauseTransition(Duration.seconds(0.2));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				GameOverStage gameover = new GameOverStage();
				gameover.setStage(n,stage,gametimer);
			}
		});
	}

	public Stage getStage(){
		return this.stage;
	}



}

