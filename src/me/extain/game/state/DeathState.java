package me.extain.game.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.extain.game.Main;
import me.extain.game.graphics.gui.GuiButton;
import me.extain.game.graphics.gui.GuiHandler;
import me.extain.game.graphics.image.ImageLoader;

public class DeathState extends State {

	private GuiHandler guiHandler = new GuiHandler();

	private BufferedImage background;
	
	private int score;

	public DeathState(Main main, int score) {
		super(main);
		
		this.score = score;
		
		background = ImageLoader.loadImage("/death-screen-background.png");

		guiHandler.addGuiObject(new GuiButton(main, "Main Menu", 100 + 100 / 2, 300, 100, 20, 0));
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
		guiHandler.update();

		if (guiHandler.getOutputID() == 0) {
			main.getStateMachine().setState(new MainMenuState(main));
		}
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(background, 0, 0, null);
		graphics.setColor(Color.WHITE);
		graphics.drawString("Score: " + score, 400 / 2, 270);
		guiHandler.render(graphics);
	}

}
