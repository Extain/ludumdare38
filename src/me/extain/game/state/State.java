package me.extain.game.state;

import java.awt.Graphics2D;

import me.extain.game.Main;

public abstract class State {
	
	protected Main main;
	
	public State(Main main) {
		this.main = main;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void render(Graphics2D graphics);

}
