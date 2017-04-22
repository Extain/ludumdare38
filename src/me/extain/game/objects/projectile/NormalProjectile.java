package me.extain.game.objects.projectile;

import java.awt.Color;
import java.awt.Graphics2D;

import me.extain.game.Main;

public class NormalProjectile extends Projectile {
	
	public NormalProjectile(Main main, float posX, float posY) {
		super(main, posX, posY);
		width = 3;
		height = 8;
	}

	@Override
	public void update() {
		super.update();
		posY += velY;
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect((int) posX, (int) posY, (int) width, (int) height);
	}
}
