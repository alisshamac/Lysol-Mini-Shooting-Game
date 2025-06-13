package LysolGame;

import javafx.scene.image.Image;

public class Pill extends Sprite implements Powerup  {
	public final static Image PILL_IMAGE = new Image("imagefiles/pill.png",Powerup.PUP_WIDTH,Powerup.PUP_WIDTH,false,false);

	public Pill(int x, int y){
		super(x,y);
		this.loadImage(Pill.PILL_IMAGE);

	}
	@Override
	public void effect(SprayCan spraycan) {
		spraycan.setStrength(spraycan.getStrength() + 50);
		System.out.println(">>>>>>>>>>>>>>>+50 SHIP STRENGTH");

	}

}
