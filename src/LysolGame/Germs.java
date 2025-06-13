package LysolGame;

import java.util.Random;

import LysolGame.GameStage;
import LysolGame.Sprite;
import javafx.scene.image.Image;

public class Germs extends Sprite {
	public static final int MAX_GERMS_SPEED = 5;
	public final static Image GERMS_IMAGE = new Image("imagefiles/smallGerm.png",Germs.GERMS_WIDTH,Germs.GERMS_WIDTH,false,false);
	public final static int GERMS_WIDTH=50;
	private boolean alive;
	//attribute that will determine if a germs will initially move to the right
	private boolean moveRight;
	private int speed;


	Germs(int x, int y){
		super(x,y);
		this.alive = true;
		this.loadImage(Germs.GERMS_IMAGE);
		/*
		 *TODO: Randomize speed of germs and moveRight's initial value
		 */
		Random r = new Random();
		this.speed = r.nextInt(Germs.MAX_GERMS_SPEED)+1;

		int m = r.nextInt(2);
		if (m == 0){
			this.moveRight = true;
		}else{
			this.moveRight = false;
		}

	}

	//method that changes the x position of the germs
	void move(){
		/*
		 * TODO: 				If moveRight is true and if the germs hasn't reached the right boundary yet,
		 *    						move the germs to the right by changing the x position of the germs depending on its speed
		 *    					else if it has reached the boundary, change the moveRight value / move to the left
		 * 					 Else, if moveRight is false and if the germs hasn't reached the left boundary yet,
		 * 	 						move the germs to the left by changing the x position of the germs depending on its speed.
		 * 						else if it has reached the boundary, change the moveRight value / move to the right
		 */

		if (moveRight==true){
			this.x += this.speed;
			if (this.x > GameStage.WINDOW_WIDTH - Germs.GERMS_WIDTH){
				this.moveRight = false;
			}
		}else{
			this.x -= this.speed;
			if (this.x < 0){
				this.moveRight = true ;
			}
		}
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


	public boolean isMoveRight() {
		return moveRight;
	}

	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}
}
