package LysolGame;

import javafx.scene.image.Image;

public class Tablet extends Sprite implements Powerup {
	public final static Image TABLET_IMAGE = new Image("imagefiles/tablet.png",Powerup.PUP_WIDTH,Powerup.PUP_WIDTH,false,false);
	public Tablet(int x, int y){
		super(x,y);
		this.loadImage(Tablet.TABLET_IMAGE);

	}
	@Override
	public void effect(SprayCan spraycan) {
		spraycan.setImmune(true);
	}

}
