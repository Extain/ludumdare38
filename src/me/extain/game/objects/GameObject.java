package me.extain.game.objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

import me.extain.game.Main;
import me.extain.game.objects.component.Component;
import me.extain.game.objects.effects.Effect;
import me.extain.game.objects.powerup.Powerup;

public abstract class GameObject {

	protected Main main;

	protected ArrayList<Component> components = new ArrayList<Component>();
	protected ArrayList<Powerup> powerups = new ArrayList<Powerup>();
	
	protected ArrayList<Powerup> toRemovePow = new ArrayList<Powerup>();

	public boolean isInfected;
	public boolean isDead;

	public GameObject(Main main, float posX, float posY) {
		this.main = main;
		this.posX = posX;
		this.posY = posY;
	}

	protected float posX, posY;
	protected float width, height;
	protected float velX, velY;
	protected float health, maxHealth;

	public abstract void update();

	public abstract void render(Graphics2D graphics);

	public abstract void componentEvent(String tag, GameObject object);

	public void updateComponents() {
		for (Component com : components)
			com.update(main, this);
	}

	public void renderComponents(Graphics2D graphics) {
		for (Component com : components)
			com.render(graphics);
	}

	public void updatePowerups() {
		for (Powerup pow : powerups) {
			if (pow.shouldRemove()) {
				toRemovePow.add(pow);
			}
			else {
				pow.update();
			}
		}
	}

	public void addComponent(Component component) {
		this.components.add(component);
	}

	public void addPowerup(Powerup powerup) {
		this.powerups.add(powerup);
	}

	public void removePowerup(Effect effect) {
		for (Powerup powerup : powerups) {
			if (powerup.getEffect() == effect) {
				powerups.remove(powerup);
			}
		}
	}

	public Powerup getPowerup(Effect effect) {
		for (Powerup powerup : powerups) {
			if (powerup.getEffect() == effect)
				return powerup;
		}

		return null;
	}

	public Component getComponent(String component) {
		for (Component com : components) {
			if (com.getTag().equalsIgnoreCase(component))
				return com;
		}

		return null;
	}

	public void removeComponent(String tag) {
		for (Component com : components)
			if (com.getTag().equalsIgnoreCase(tag))
				components.remove(com);
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

}
