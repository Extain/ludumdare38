package me.extain.game.graphics.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import me.extain.game.Main;
import me.extain.game.audio.AudioPlayer;

public class GuiButton extends GUI {

	private String text;
	private float width, height;

	private Main main;
	
	private AudioPlayer select;

	public GuiButton(Main main, String text, float posX, float posY, float width, float height, int id) {
		this.main = main;
		this.text = text;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.id = id;
		
		select = new AudioPlayer("/sounds/select.wav");

	}

	@Override
	public void update() {
		checkClicked();
	}

	private void checkClicked() {
		Rectangle bounds = new Rectangle((int) posX, (int) posY, (int) width, (int) height);

		hovered = bounds.contains(main.getInput().getMouseX(), main.getInput().getMouseY());
		
		if (main.getInput().isButton(MouseEvent.BUTTON1) && hovered) {
			interacted = true;
			select.play();
		}
		else
			interacted = false;
	}

	@Override
	public void render(Graphics2D graphics) {
		// Outside Rect
		graphics.setColor(Color.BLACK);
		graphics.drawRect((int) posX - 1, (int) posY - 1, (int) width + 1, (int) height + 1);

		// Inside Rect
		if (hovered)
			graphics.setColor(Color.LIGHT_GRAY);
		else
			graphics.setColor(Color.GRAY);
		graphics.fillRect((int) posX, (int) posY, (int) width, (int) height);
		
		// Draw Text
		graphics.setColor(Color.WHITE);
		graphics.drawString(text, (int) posX + width / 4, (int) posY + height / 2);

	}

}
