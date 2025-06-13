package LysolGame;

import javafx.scene.image.Image;

public class BossBullet extends Sprite {
	private final int BOSSBULLET_SPEED = 20;
	public final static Image BOSSBULLET_IMAGE = new Image("imagefiles/bigGerm.png",BossBullet.BOSSBULLET_WIDTH,BossBullet.BOSSBULLET_WIDTH,false,false);
	public final static int BOSSBULLET_WIDTH = 20;
	private int damage = 10;

	public BossBullet(int x, int y){
		super(x,y);
		this.loadImage(BossBullet.BOSSBULLET_IMAGE);
	}


	//method that will move/change the x position of the bullet
	public void move(){
		/*
		 * TODO: Change the x position of the bullet depending on the bullet speed.
		 * 					If the x position has reached the right boundary of the screen,
		 * 						set the bullet's visibility to false.
		 */
		if (this.x <0){
			this.setVisible(false);
		}else{
			this.x -= this.BOSSBULLET_SPEED;
		}



	}


	public int getBulletSpeed() {
		return BOSSBULLET_SPEED;
	}


	public int getDamage() {
		return damage;
	}



}