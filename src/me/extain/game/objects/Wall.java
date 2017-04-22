package me.extain.game.objects;

import java.awt.Color;
import java.awt.Graphics2D;

import me.extain.game.Main;
import me.extain.game.objects.component.Collider;

public class Wall extends GameObject {

	public Wall(Main main, float posX, float posY) {
		super(main, posX, posY);
		this.addComponent(new Collider());
		
		width = 20;
		height = 600;
	}

	@Override
	public void update() { updateComponents(); }

	@Override
	public void render(Graphics2D graphics) {
		graphics.setColor(new Color(255, 193, 204));
		graphics.fillRect((int) posX, (int) posY, (int) width,(int) height);
	}

	@Override
	public void componentEvent(String tag, GameObject object) {
		
	}

}
