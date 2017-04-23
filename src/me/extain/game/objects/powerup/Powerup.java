package me.extain.game.objects.powerup;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.extain.game.Main;
import me.extain.game.audio.AudioPlayer;
import me.extain.game.objects.GameObject;
import me.extain.game.objects.component.Collider;
import me.extain.game.objects.player.Player;
import me.extain.game.utils.Delay;

public abstract class Powerup extends GameObject {

	private String tag;

	public enum Effect {
		SHOOTFAST
	}

	protected Effect powEffect;

	protected BufferedImage image;

	protected Delay powTimeDelay;
	
	protected AudioPlayer pickup;

	public Powerup(Main main, float posX, float posY) {
		super(main, posX, posY);

		this.addComponent(new Collider());

		powTimeDelay = new Delay(300);
		
		pickup = new AudioPlayer("/sounds/powerup.wav");
		pickup.setVolume(-20f);

		width = 24;
		height = 24;
	}

	public void update() {
		updateComponents();

		posX += velX;
		posY += velY;
	}

	public Effect getEffect() {
		return powEffect;
	}

	public void render(Graphics2D graphics) {
		graphics.drawImage(image, (int) posX, (int) posY, (int) width, (int) height, null);
	}

	public void componentEvent(String tag, GameObject object) {
		if (object instanceof Player) {
			isDead = true;
			pickup.play();
			object.addPowerup(this);
			powTimeDelay.restart();
		}
	}
	
	public Delay getDelay() { 
		return powTimeDelay;
	}

	public void startTime() {
		powTimeDelay.terminate();
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

}
