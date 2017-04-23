package me.extain.game.graphics.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GuiHandler {
	
	private ArrayList<GUI> guiObjects = new ArrayList<GUI>();
	
	private int outputID = -1;
	
	public void update() {
		boolean interacted = false;
		boolean hovered = false;
		
		for (GUI gui : guiObjects) {
			gui.update();
			
			if (gui.isInteracted()) {
				outputID = gui.getID();
				interacted = true;
			}
			
			if (gui.hovered) {
				hovered = true;
			}
		}
		
		if (interacted == false) {
			outputID = -1;
		}
	}
	
	public void render(Graphics2D graphics) {
		for (GUI gui : guiObjects) {
			gui.render(graphics);
		}
	}
	
	public void addGuiObject(GUI gui) {
		guiObjects.add(gui);
	}
	
	public int getOutputID() {
		return outputID;
	}
}
