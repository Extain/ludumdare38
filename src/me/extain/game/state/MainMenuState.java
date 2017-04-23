package me.extain.game.state;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.extain.game.Main;
import me.extain.game.graphics.gui.GuiButton;
import me.extain.game.graphics.gui.GuiHandler;
import me.extain.game.graphics.image.ImageLoader;

public class MainMenuState extends State {
	
	private BufferedImage background;
	
	private GuiHandler guiHandler = new GuiHandler();

	public MainMenuState(Main main) {
		super(main);
		
		background = ImageLoader.loadImage("/main-menu-background.png");
		guiHandler.addGuiObject(new GuiButton(main, "Play Game", 100 + 100 / 2, 200, 100, 20, 0));
		guiHandler.addGuiObject(new GuiButton(main, "Quit Game", 100 + 100 / 2, 240, 100, 20, 1));
	}

	@Override
	public void init() { }

	@Override
	public void update() {
		guiHandler.update();
		
		if (guiHandler.getOutputID() == 0) {
			main.getStateMachine().setState(new GameState(main));
		}
		
		if (guiHandler.getOutputID() == 1) {
			System.exit(1);
		}
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(background, 0, 0, 400, 600, null);
		guiHandler.render(graphics);
	}

}
