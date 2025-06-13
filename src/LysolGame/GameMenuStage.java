package LysolGame;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class GameMenuStage {
	private StackPane pane;
	private Scene scene;
	private Stage stage;
	private Canvas canvas;
	private GraphicsContext gc;
	private final Image bg = new Image("imagefiles/aboutScreen.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	private final Image title = new Image("imagefiles/GameTitle.png",400,175,false,false);
	private Button startbtn = new Button("New Game");
	private Button instbtn = new Button("Instructions");
	private Button aboutbtn = new Button("About");


	//the class constructor
	public GameMenuStage() {
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.gc.drawImage(this.bg, 0, 0);
		this.gc.drawImage(this.title, GameStage.WINDOW_WIDTH*0.4, GameStage.WINDOW_HEIGHT*0.1);



		//position buttons
		this.startbtn.setTranslateY(30);
		this.startbtn.setTranslateX(120);
		this.instbtn.setTranslateY(80);
		this.instbtn.setTranslateX(120);
		this.aboutbtn.setTranslateY(130);
		this.aboutbtn.setTranslateX(120);


		this.addEventHandler(startbtn);
		this.addEventHandler(instbtn);
		this.addEventHandler(aboutbtn);

		//set stage elements here
		pane.getChildren().add(this.canvas);
		pane.getChildren().add(startbtn);
		pane.getChildren().add(aboutbtn);
		pane.getChildren().add(instbtn);
		this.stage = stage;


		this.stage.setTitle("Lysol Disinfecter Menu");
		this.stage.setScene(this.scene);



		this.stage.show();
	}

	private void addEventHandler(Button btn) {
		if (btn.equals(this.startbtn)){
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				GameStage theGameStage = new GameStage();
				theGameStage.setStage(stage);

				}
			});
		} else if (btn.equals(this.instbtn)){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				public void handle(MouseEvent arg0) {
					InstructionsStage instStage = new InstructionsStage();
					instStage.setStage(stage);


				}
			});
		}else if (btn.equals(this.aboutbtn)){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				public void handle(MouseEvent arg0) {
					AboutStage aboutStage = new AboutStage();
					aboutStage.setStage(stage);

				}
			});
			}



	}





}

