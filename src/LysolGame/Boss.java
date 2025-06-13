package LysolGame;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class Boss extends Germs {
	public static final int MAX_BOSS_SPEED = 5;
	public final static Image BOSS_IMAGE = new Image("imagefiles/bigGerm.png",Boss.BOSS_WIDTH,Boss.BOSS_WIDTH,false,false);
	public final static int BOSS_WIDTH=100;
	private ArrayList<BossBullet> bullets;
	private boolean alive;
	//attribute that will determine if a boss will initially move to the right
	private boolean moveRight;
	private int speed;
	private int health = 3000;

	Boss(int x, int y){
		super(x,y);
		this.alive = true;
		this.loadImage(Boss.BOSS_IMAGE);
		this.bullets = new ArrayList<BossBullet>();

		Random r = new Random();
		this.speed = r.nextInt(Boss.MAX_BOSS_SPEED)+1;

		this.moveRight = true;

	}

	void move(){

		if (moveRight==true){
			this.x += this.speed;
			if (this.x > GameStage.WINDOW_WIDTH - Boss.BOSS_WIDTH){
				this.moveRight = false;
			}
		}else{
			this.x -= this.speed;
			if (this.x < 0){
				this.moveRight = true ;
			}
		}
	}

	public void shoot(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x);
		int y = (int) (this.y + this.height*0.5);
		/*
		 * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
		 */
		BossBullet bullet= new BossBullet(x,y);
		this.bullets.add(bullet);
		bullet.move();

    }

	//getter
	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}

	public void die(){
    	this.alive = false;
    	this.setVisible(false);
    }

	public int getHealth() {
		return this.health;
	}

	public void minusHealth(int h) {
		this.health = this.health - h;
	}


	public boolean isMoveRight() {
		return moveRight;
	}

	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public ArrayList<BossBullet> getBullets() {
		return this.bullets;
	}
}
