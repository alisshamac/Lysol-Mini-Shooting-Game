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
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class GameOverStage {
	private StackPane pane;
	private Scene scene;
	private Stage stage;
	private GraphicsContext gc;
	private Canvas canvas;
	private GameTimer gametimer;
	private final Image bg = new Image("imagefiles/gameOverBg.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	private final Image germ = new Image("imagefiles/smallGerm.png",50,50,false,false);
	private Button exitbtn = new Button("Exit Game");
	private Button menubtn = new Button("Main Menu");


	GameOverStage(){
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

	}

	protected void setStage(int n, Stage stage,GameTimer gametimer){
		this.gc.drawImage(this.bg, 0, 0);
		this.gc.setFill(Color.BLACK);
		this.gametimer = gametimer;

		//display win or lose
		if (n==1){
			this.gc.setFont(Font.loadFont("file:Resources/fonts/DkCoolCrayon-xJyV.ttf", 60));
			this.gc.fillText("YOU WIN!", GameStage.WINDOW_WIDTH*0.32,GameStage.WINDOW_HEIGHT*0.42);

			//display final strength
			this.gc.setFont(Font.loadFont("file:Resources/fonts/DkCoolCrayon-xJyV.ttf", 20));
			String text = "Final Strength:  " + this.gametimer.getCanStrength();
			this.gc.fillText(text, GameStage.WINDOW_WIDTH*0.37, GameStage.WINDOW_HEIGHT*0.62);

		}else{
			this.gc.setFont(Font.loadFont("file:Resources/fonts/DkCoolCrayon-xJyV.ttf", 60));
			this.gc.fillText("YOU LOSE :(", GameStage.WINDOW_WIDTH*0.28,GameStage.WINDOW_HEIGHT*0.42);

			//display final strength
			this.gc.setFont(Font.loadFont("file:Resources/fonts/DkCoolCrayon-xJyV.ttf", 20));
			this.gc.fillText("Final Strength:  0", GameStage.WINDOW_WIDTH*0.37, GameStage.WINDOW_HEIGHT*0.62);
		}

		//display kill count
		this.gc.setFont(Font.loadFont("file:Resources/fonts/DkCoolCrayon-xJyV.ttf", 20));
		this.gc.drawImage(this.germ, GameStage.WINDOW_WIDTH*0.39-10, GameStage.WINDOW_HEIGHT*0.46);
		String text = "'s killed:  " + this.gametimer.getKillCount();
		this.gc.fillText(text, GameStage.WINDOW_WIDTH*0.39+40,GameStage.WINDOW_HEIGHT*0.52);


		//initialize buttons

		menubtn.setTranslateX(-10);
		menubtn.setTranslateY(100);
		this.addEventHandler(menubtn);

		exitbtn.setTranslateX(-10);
		exitbtn.setTranslateY(135);
		this.addEventHandler(exitbtn);

		//set stage elements here
		pane.getChildren().add(this.canvas);
		pane.getChildren().add(exitbtn);
		pane.getChildren().add(menubtn);
		this.stage = stage;


		this.stage.setTitle("Lysol Disinfecter GameOver");
		this.stage.setScene(this.scene);



		this.stage.show();
	}

	private void addEventHandler(Button btn) {
		if (btn.equals(this.exitbtn)){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				public void handle(MouseEvent arg0) {
					System.exit(0);
				}
			});
		}else if (btn.equals(this.menubtn)){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent arg0) {
					GameMenuStage theMenuStage = new GameMenuStage();
					theMenuStage.setStage(stage);
			}
		});
		}


	}

}
