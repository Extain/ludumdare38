package me.extain.game.graphics.gui;

import java.awt.Graphics2D;

public abstract class GUI {
	
	protected String name;
	protected int id;
	protected boolean interacted;
	protected boolean hovered;
	
	protected float posX, posY;
	
	public abstract void update();
	
	public abstract void render(Graphics2D graphics);
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isInteracted() {
		return interacted;
	}
	
	public void setInteracted(boolean interacted) {
		this.interacted = interacted;
	}
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	public float getPosX() {
		return posX;
	}
	
	public float getPosY() {
		return posY;
	}
	
	public void setPosition(float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	

}
