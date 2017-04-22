package me.extain.game.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.extain.game.Main;
import me.extain.game.graphics.image.ImageLoader;
import me.extain.game.objects.component.Collider;
import me.extain.game.state.GameState;

public class BloodCell extends GameObject {
	
	private boolean isVirus;
	private GameState state;
	
	private BufferedImage cell;

	public BloodCell(Main main, GameState state, float posX, float posY) {
		super(main, posX, posY);
		this.state = state;
		
		cell = ImageLoader.loadImage("/blood-cell.png");
		
		this.addComponent(new Collider());
		width = 24;
		height = 24;
	}

	@Override
	public void update() {
		updateComponents();
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(cell, (int) posX, (int) posY, null);
		
	}

	@Override
	public void componentEvent(String tag, GameObject object) {
		
	}
	
	public void turnToVirus() {
		this.isInfected = true;
	}

}
