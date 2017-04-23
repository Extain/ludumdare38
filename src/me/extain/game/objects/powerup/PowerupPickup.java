package me.extain.game.objects.powerup;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.extain.game.Main;
import me.extain.game.audio.AudioPlayer;
import me.extain.game.objects.GameObject;
import me.extain.game.objects.component.Collider;
import me.extain.game.objects.effects.Effect;
import me.extain.game.objects.player.Player;
import me.extain.game.utils.Timer;

public abstract class PowerupPickup extends GameObject {
	protected Effect powEffect;

	protected BufferedImage image;

	protected Timer timer;
	
	protected AudioPlayer pickupSound;
	
	private int duration = 6000;

	public PowerupPickup(Main main, float posX, float posY) {
		super(main, posX, posY);

		this.addComponent(new Collider());

		timer = new Timer(duration);
		timer.start();
		
		pickupSound = new AudioPlayer("/sounds/powerup.wav");
		pickupSound.setVolume(-20f);

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
			timer.stop();
			pickupSound.play();
			object.addPowerup(new Powerup(powEffect, duration));
		}
	}
	
	public boolean shouldRemove() {
		return timer.finished();
	}
}
