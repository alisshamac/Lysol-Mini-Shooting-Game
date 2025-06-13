package LysolGame;

import java.util.ArrayList;
import java.util.Random;
import LysolGame.Bullet;
import LysolGame.GameStage;
import LysolGame.Sprite;
import javafx.scene.image.Image;



public class SprayCan extends Sprite{
	private String name;
	private int strength = 0;
	private boolean alive;
	private boolean immune;
	protected GameStage gameStage;
	private ArrayList<Bullet> bullets;
	public final static Image CAN_IMAGE = new Image("imagefiles/sprayCan.png",SprayCan.CAN_WIDTH,SprayCan.CAN_HEIGHT,false,false);
	private final static int CAN_WIDTH = 35;
	private final static int CAN_HEIGHT = 60;

	public SprayCan(String name, int x, int y){
		super(x,y);
		this.name = name;
		Random r = new Random();
		this.setStrength(r.nextInt(51)+100);
		this.alive = true;
		this.bullets = new ArrayList<Bullet>();
		this.loadImage(SprayCan.CAN_IMAGE);
	}

	protected int setStrength(int i) {
		this.strength = i;
		return this.strength;

	}

	public int getStrength() {
		return this.strength;

	}


	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}

	public boolean isImmune(){
		if(this.immune==true){
			return true;
		}else{
		return false;}
	}

	public void setImmune(boolean i){
		if(i == true){
			this.immune = true;
		}
		else{ this.immune = false;}
	}


	public String getName(){
		return this.name;
	}

	public void die(){
    	this.alive = false;
    }

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

	//method called if spacebar is pressed
	public void shoot(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x);
		int y = (int) (this.y + this.height*0.005);
		/*
		 * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
		 */
		Bullet bullet= new Bullet(x,y);
		this.bullets.add(bullet);
		bullet.setDamage(this.getStrength());
		bullet.move();

    }

	//method called if up/down/left/right arrow key is pressed.
	public void move() {
		/*
		 *TODO: 		Only change the x and y position of the ship if the current x,y position
		 *				is within the gamestage width and height so that the ship won't exit the screen
		 */
		if (this.isAlive()==true){
			if (this.y < GameStage.Y_MARGIN){
				this.y = GameStage.Y_MARGIN;
			}else if (this.y >  GameStage.WINDOW_HEIGHT-SprayCan.CAN_WIDTH){
				this.y = GameStage.WINDOW_HEIGHT-SprayCan.CAN_HEIGHT;
			}else{
				this.y += this.dy;
			}

			if (this.x > GameStage.WINDOW_WIDTH-SprayCan.CAN_WIDTH) {
				this.x = GameStage.WINDOW_WIDTH-SprayCan.CAN_WIDTH;
			} else if (this.x< 0){
				this.x = 0;
			}else{
				this.x += this.dx;
			}


		}
	}




}
