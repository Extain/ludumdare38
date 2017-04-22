package me.extain.game.objects.component;

import java.awt.Graphics2D;

import me.extain.game.Main;
import me.extain.game.objects.GameObject;

public abstract class Component {
	
	protected String tag = null;
	public abstract void update(Main main, GameObject object);
	public abstract void render(Graphics2D graphics);
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
}