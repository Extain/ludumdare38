package me.extain.game.objects.powerup;

import me.extain.game.Main;
import me.extain.game.graphics.image.ImageLoader;
import me.extain.game.objects.effects.Effect;

public class FastShoot extends PowerupPickup {

	public FastShoot(Main main, float posX, float posY) {
		super(main, posX, posY);
		
		image = ImageLoader.loadImage("/shoot-fast-powerup.png");
		
		powEffect = Effect.SHOOTFAST;
	}
}
