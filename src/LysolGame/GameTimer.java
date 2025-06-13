package LysolGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer{

	private GameStage gamestage;
	private GraphicsContext gc;
	private Scene theScene;
	private SprayCan mySprayCan;
	private Boss BossVirus;
	private Powerup pill;
	private Powerup tablet;
	private ArrayList<Germs> germs;
	public static final int INITIAL_NUM_GERMS = 7;
	public static final int MAX_NUM_GERMS = 3;
	public static final int SPRAYCAN_X = 100;
	public static final int SPRAYCAN_Y = 250;
	private final Image bg = new Image("imagefiles/bg.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	private final Image germ = new Image("imagefiles/smallGerm.png",50,50,false,false);
	private boolean hereBoss = false;
	private boolean herePill = false;
	private boolean hereTablet = false;
	private boolean gameOver = false;
	private int time = 60;
	private int killCount = 0;
	private int pilltime = 4;
	private int tablettime = 4;
	private int finishedImmune;

	GameTimer(GraphicsContext gc, Scene theScene, GameStage theStage){
		this.gc = gc;
		this.theScene = theScene;
		this.gamestage = theStage;

		//set font details for all
		this.gc.setFont(Font.loadFont("file:Resources/fonts/DkCoolCrayon-xJyV.ttf", 20));
		this.gc.setFill(Color.BLACK);

		this.mySprayCan = new SprayCan("Going merry",GameTimer.SPRAYCAN_X,GameTimer.SPRAYCAN_Y);

		//instantiate the ArrayList of Germs
		this.germs = new ArrayList<Germs>();


		//call all spawn methods
		this.periodicSpawnGerms();
		this.periodicSpawnPowerup();
		this.delayedSpawnBoss();

		//call method to handle mouse click event
		this.handleKeyPressEvent();


		//call countdown method
		this.countdownTimer();

	}



	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

		/*
		 *  display all graphics
		 */
		this.gc.drawImage(this.bg, 0, 0);
		displaySprayCanStrength();
		displayKillCount();
		displayCountdown();


		/*
		 *  Call move methods
		 */
		this.mySprayCan.move();
		this.moveBullets();
		this.moveGerms();



		/*
		 *  Call render methods
		 */


		this.mySprayCan.render(this.gc);
		this.renderBullets();
		this.renderGerms();


		//if boss is in game, it renders and moves
		if (this.hereBoss == true){
			this.moveBoss();
			this.moveBossBullets();
			this.renderBoss();
			this.renderBossBullets();

		}

		//if pill is in game, it renders
		if (this.herePill == true){
			this.renderPowerup(0);
		}

		//if tablet is in game, it renders
		if (this.hereTablet == true){
			this.renderPowerup(1);
		}

		//if tablet is activated and SprayCan is immune, it diplays immunity countdown
		if (this.mySprayCan.isImmune()){
			displayImmunity();
		}




	}





	/*
	 *
	 * methods for all changing graphics
	 *
	 */

	private void displaySprayCanStrength(){
		String text = "Strength: " + Integer.toString(mySprayCan.getStrength());
		this.gc.fillText(text, 30, 40);

	}



	private void displayKillCount(){
		this.gc.drawImage(this.germ, GameStage.WINDOW_WIDTH-115, 10);
		String text = ": " + Integer.toString(this.killCount);
		this.gc.fillText(text, GameStage.WINDOW_WIDTH-65, 40);

	}

	private void displayCountdown(){

		String text =Integer.toString(this.time);
		this.gc.fillText(text, (GameStage.WINDOW_WIDTH*0.5)-25, 40);


	}

	private void displayImmunity(){

		this.gc.fillText("You Are Immune!", (GameStage.WINDOW_WIDTH*0.5)-90, 100);


	}





	/*
	 *
	 * sprite render methods
	 *
	 */

	private void renderGerms() {
		for (Germs g : this.germs){
			g.render(this.gc);
		}
	}

	private void renderBossBullets() {
		//Loop through the bullets arraylist of BossVirus
		for (BossBullet b: this.BossVirus.getBullets()){
			b.render(this.gc);
		}

	}

	private void renderBullets() {

		//Loop through the bullets arraylist of mySprayCan

		for (Bullet b: this.mySprayCan.getBullets()){
			b.render(this.gc);
		}

	}

	private void renderBoss() {
		this.BossVirus.render(this.gc);
	}

	private void renderPowerup(int n) {
		if (n == 0){
			((Sprite) this.pill).render(this.gc);
			this.pillCollide(mySprayCan);
		}else{
			((Sprite) this.tablet).render(this.gc);
			this.tabletCollide(mySprayCan);
		}

	}






	/*
	 *
	 * methods for all sprite spawns
	 *
	 */

	//method that will spawn/instantiate three germs at a random x,y location
	private void spawnGerms(int max){
		Random r = new Random();
		for(int i=0;i<max;i++){
			int x = r.nextInt(GameStage.WINDOW_WIDTH-Germs.GERMS_WIDTH-(GameStage.WINDOW_WIDTH/2)) + (GameStage.WINDOW_WIDTH/2);
			int y = r.nextInt(GameStage.WINDOW_HEIGHT-Germs.GERMS_WIDTH-GameStage.Y_MARGIN)+GameStage.Y_MARGIN;
			/*
			 *TODO: Add a new object Germs to the germs arraylist
			 */
			Germs germs= new Germs(x,y);
			this.germs.add(germs);

		}

	}
	//method that will periodically spawn 3 enemies after 5 seconds
	private void periodicSpawnGerms(){
		this.spawnGerms(GameTimer.INITIAL_NUM_GERMS);

		Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	            if(gameOver==true){
	            	timer.cancel();

	            }else{
	            	spawnGerms(GameTimer.MAX_NUM_GERMS);
	            	System.out.println(">>>>>>>>>>>>>>>Spawning enemy");
	            }
	        }
	    }, 5000,5000);
	}

	//method that will spawn the Boss
	private void spawnBoss(){
		Random r = new Random();
		int y = r.nextInt(GameStage.WINDOW_HEIGHT-Germs.GERMS_WIDTH-GameStage.Y_MARGIN-Boss.BOSS_WIDTH)+GameStage.Y_MARGIN;
		System.out.println(">>>>>>>>>>>>>>>Spawning Boss");
		this.BossVirus = new Boss(GameStage.WINDOW_WIDTH-Boss.BOSS_WIDTH/2,y);
	}

	//delays spawnboss by 30 seconds
	private void delayedSpawnBoss(){
		Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        public void run() {
	        	if (gameOver==true){
		            	timer.cancel();
		        }else
	            	spawnBoss();
					hereBoss = true;
					periodicSpawnBossBullets();

	        }
	    }, 30000);
	}


	private void periodicSpawnBossBullets(){

		Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	            if(hereBoss==false || gameOver == true){
	            	timer.cancel();

	            }else{
	            	BossVirus.shoot();
	            	System.out.println(">>>>>>>>>>>>>>>Boss Shoots");
	            }
	        }
	    }, 2000,2000);

	}

	private void spawnPowerup(){
		Random r = new Random();

			int x = r.nextInt(GameStage.WINDOW_WIDTH/3);
			int x2 = r.nextInt(GameStage.WINDOW_WIDTH/3);
			int y = r.nextInt(GameStage.WINDOW_HEIGHT-Powerup.PUP_WIDTH-GameStage.Y_MARGIN)+GameStage.Y_MARGIN;
			int y2 = r.nextInt(GameStage.WINDOW_HEIGHT-Powerup.PUP_WIDTH-GameStage.Y_MARGIN)+GameStage.Y_MARGIN;

			this.pill = new Pill(x,y);
			this.tablet = new Tablet(x2,y2);

	}

	//method that will periodically spawn powerups after 10 seconds
	private void periodicSpawnPowerup(){
		Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	            if(gameOver==true){
	            	timer.cancel();

	            }else{
	            	spawnPowerup();
	            	herePill = true;
	            	hereTablet = true;

	            }
	        }
	    }, 10000,10000);


	    //make powerup disappear after 5 seconds (it takes 1 second for them to render so wait for 4 secs to make them disappear)



		Timer timer2 = new Timer();
	    timer2.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	            if(gameOver==true){
	            	timer.cancel();

	            }

	            if (herePill == true && pilltime>0){
	            	pilltime--;
	            }else if (pilltime<=0){
	            	((Sprite) pill).setVisible(false);
	            	herePill = false;
	            	pilltime = 4;
	            }

	            if (hereTablet == true && tablettime>0){
	            	tablettime--;
	            }else if (tablettime<=0){
	            	((Sprite) tablet).setVisible(false);
	            	hereTablet = false;
	            	tablettime = 4;
	            }
	        }
	    }, 10000,1000);
	}



	/*
	 *
	 * all move methods
	 *
	 */

	private void moveBossBullets(){
			//create a local arraylist of Bullets for the bullets 'shot' by the ship
			ArrayList<BossBullet> bList = this.BossVirus.getBullets();

			//Loop through the bullet list and check whether a bullet is still visible.
			for(int i = 0; i < bList.size(); i++){
				BossBullet b = bList.get(i);
				//  If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.

				if (b.isVisible() == true){
					b.move();
					this.bossBulletCollides(b);

				}else{
					bList.remove(b);
				}
			}

	}


	//method that will move the bullets shot by a ship
	private void moveBullets(){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.mySprayCan.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			//  If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.

			if (b.isVisible() == true){
				b.move();
				b.setDamage(this.mySprayCan.getStrength());
				this.bulletCollides(b);

			}else{
				bList.remove(b);
			}
		}
	}


	private void moveGerms(){
		//Loop through the germs arraylist
		for(int i = 0; i < this.germs.size(); i++){
			Germs g = this.germs.get(i);
			/*
			 * TODO:  *If a germs is alive, move the germs. Else, remove the germs from the germs arraylist.
			 */
			if (g.isAlive() == true){
				g.move();
				this.germsCollide(g);
			}else{
				this.germs.remove(g);
			}


		}
	}

	private void moveBoss(){
		Boss b = this.BossVirus;
		if (b.isAlive() == true){
			b.move();
			String text =" " + b.getHealth() + " "; 	//displays current boss health
			this.gc.fillText(text, b.getX()+20,b.getY()-10);
			this.bossCollide(b);

		}

	}


	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMySprayCan(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMySprayCan(code);
		            }
		        });
    }


		//method that will move the ship depending on the key pressed
	private void moveMySprayCan(KeyCode ke) {
		if(ke==KeyCode.UP) this.mySprayCan.setDY(-10);

		if(ke==KeyCode.DOWN) this.mySprayCan.setDY(10);

		if(ke==KeyCode.LEFT) this.mySprayCan.setDX(-10);

		if(ke==KeyCode.RIGHT) this.mySprayCan.setDX(10);

		if(ke==KeyCode.SPACE) this.mySprayCan.shoot();

		System.out.println(ke+" key pressed.");
   	}

		//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMySprayCan(KeyCode ke){
		this.mySprayCan.setDX(0);
		this.mySprayCan.setDY(0);
	}








	/*
	 *
	 * all collision methods
	 *
	 */


	private void bossBulletCollides(BossBullet b){

		if (b.collidesWith(this.mySprayCan)){
			if (!this.mySprayCan.isImmune()){ //if spray can not immune, lessen strength by 10
				this.mySprayCan.setStrength(this.mySprayCan.getStrength() - b.getDamage());
				b.setVisible(false);
			}
			if (this.mySprayCan.getStrength()<=0){
				this.lose();
			}
		}
	}


	private void bulletCollides(Bullet b){
		for(int j= 0 ; j<this.germs.size();j++){
			if (b.collidesWith(this.germs.get(j))){

				//germs disappears
				this.germs.get(j).die();
				b.setVisible(false);

				//adds to kill count
				this.killCount +=1;
			}
		}
		if (hereBoss == true){ //if boss is here
			if (b.collidesWith(this.BossVirus)){

				this.BossVirus.minusHealth(b.getDamage()); //boss health lessens by ship strength
				b.setVisible(false);
				if (this.BossVirus.getHealth() <=0){
					this.BossVirus.die(); //if health is <= 0, boss dies
					hereBoss = false;

					}

			}
		}

	}


	private void germsCollide(Germs g){
		if (g.collidesWith(this.mySprayCan)==true){
			g.die();
			if (!this.mySprayCan.isImmune()){ //if spray can not immune, record damage to strength
				this.mySprayCan.setStrength(mySprayCan.getStrength()-30);
			}
			//if ship strength is 0 it dies
			if (this.mySprayCan.getStrength() <= 0){
				this.lose();
			}
		}
	}

	private void bossCollide(Boss b){
			if (b.collidesWith(this.mySprayCan)==true){
				if (!this.mySprayCan.isImmune()){ //if spray can not immune, record damage to strength
					this.mySprayCan.setStrength(mySprayCan.getStrength()-50);
				}

				//to avoid continuous contact, bounce boss when collide with can
				if (b.isMoveRight()==true){
					b.setMoveRight(false);
				}else{
					b.setMoveRight(true);
				}

				if (this.mySprayCan.getStrength() <= 0){
					this.lose();
				}
		}



	}

	private void pillCollide(SprayCan can){
		if (((Sprite) this.pill).collidesWith(can)==true){
			System.out.println(">>>>>>>>>>>>>>>Pill Aqcuired");
			this.pill.effect(can); //add 50 to strength
			pilltime = 4; //resets pill time for rendering
			herePill = false;
		}
	}
	private void tabletCollide(SprayCan can){

		if (((Sprite) this.tablet).collidesWith(can)==true){
				System.out.println(">>>>>>>>>>>>>>>Tablet Aqcuired");
				tablet.effect(can); 	//sets spray can isImmune to true
				finishedImmune = this.time-3; //records time 3 seconds from current time. (when to top immunity)

				Timer timer = new Timer();
			    timer.scheduleAtFixedRate(new TimerTask() {
			        public void run() {
			            if(gameOver==true){
			            	timer.cancel();

			            }else if (can.isImmune() && time != finishedImmune){
			            	((Sprite) tablet).setVisible(false); //tablet disappears
			            	hereTablet = false;
			            	System.out.println(">>>>>>>>>>>>YOU ARE IMMUNE");
			            }else if (can.isImmune() && time == finishedImmune){
			            	can.setImmune(false); //no longer immune
			            }
			        }
			    }, 0,1000);//evaluates if can is immune every second

			    tablettime = 4; //resets tablet time for rendering

			}

}


	/*
	 *
	 * countdown method
	 *
	 */

	private void countdownTimer(){
		Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	            if(gameOver==true){
	                timer.cancel();
	            }else if (time == 0){
	            	win();	//if time is 0, call win
	            }else {
	                time--; //decrease time
	            }
	        }
	    }, 0,1000);
	}





	/*
	 *
	 * all methods to determine result
	 *
	 */

	private void lose(){
		this.mySprayCan.die();
		gameOver = true;
		gamestage.flashGameOver(0);
		System.out.println(">>>>>>>>>>>Player Loses");


	}


	private void win(){
    	gameOver = true;
    	gamestage.flashGameOver(1);
    	System.out.println(">>>>>>>>>>>Player wins!");


	}


	public int getKillCount(){
		return this.killCount;
	}

	public int getCanStrength(){
		return this.mySprayCan.getStrength();
	}


}
