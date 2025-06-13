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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class AboutStage {
	private StackPane pane;
	private Scene scene;
	private Stage stage;
	private Canvas canvas;
	private GraphicsContext gc;
	private final Image bg = new Image("imagefiles/aboutScreen.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	Button backbtn = new Button("Back");


	//the class constructor
	public AboutStage() {
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.gc.drawImage(this.bg, 0, 0);

		//Text
		this.gc.setFont(Font.loadFont("file:Resources/fonts/DkCoolCrayon-xJyV.ttf", 80));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText("About", GameStage.WINDOW_WIDTH*0.5, 120);

		this.gc.setFont(Font.loadFont("file:Resources/fonts/Blogger Sans.ttf.ttf", 12));
		this.gc.setFill(Color.BLACK);
		this.gc.setTextAlign(TextAlignment.CENTER);
		this.gc.fillText("Developed by:\nAlissha Mae Cardona (2020-12547 || W - 5L)\n\nImages used:\nAmoeba icon made by Smashicons from www.flaticon.com\nVirus icon made by Good Ware from www.flaticon.com\nSpray-paint icon made by freepik from www.flaticon.com\nPaper texture background by Vectorig from www.istockphoto.com\n\nReferences:\nbase code from cmsc 22 miniprojtemplate, uplb\nhttps://docs.oracle.com/javase/8/javafx/api/javafx/animation/AnimationTimer.htm\nhttps://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledExecutorService.html\nhttps://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledThreadPoolExecutor.html\nhttps://stackoverflow.com/questions/47655695/javafx-countdown-timer-in-label-settext\nhttps://stackoverflow.com/questions/9966136/javafx-periodic-background-task\nhttps://mkyong.com/java/java-scheduledexecutorservice-examples/\nhttps://edencoding.com/periodic-background-tasks/\nhttps://edencoding.com/animation-timer-pausing/\nhttps://docs.oracle.com/javafx/2/text/jfxpub-text.html\nhttps://techdora.com/how-to-add-eclipse-resource-folder-and-files/\nhttps://stackoverflow.com/questions/33357295/display-variable-int-in-javafx", GameStage.WINDOW_WIDTH*0.65, 160);



		//initialize buttons
		Button backbtn = new Button("Back");
		backbtn.setTranslateX(-295);
		backbtn.setTranslateY(-200);
		this.addEventHandler(backbtn);

		//set stage elements here
		pane.getChildren().add(this.canvas);
		pane.getChildren().add(backbtn);

		this.stage = stage;


		this.stage.setTitle("Lysol Disinfecter About");
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