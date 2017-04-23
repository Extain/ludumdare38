package me.extain.game.objects.player;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import me.extain.game.Main;
import me.extain.game.audio.AudioPlayer;
import me.extain.game.graphics.image.ImageLoader;
import me.extain.game.objects.GameObject;
import me.extain.game.objects.component.Collider;
import me.extain.game.objects.effects.Effect;
import me.extain.game.objects.projectile.NormalProjectile;
import me.extain.game.objects.projectile.Projectile;
import me.extain.game.utils.Delay;
import me.extain.game.utils.Time;

public class Player extends GameObject {

	private BufferedImage ship;

	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ArrayList<Projectile> toRemoveProj = new ArrayList<Projectile>();

	private Delay attackDelay = new Delay(300);
	
	protected boolean triShot;
	
	private AudioPlayer shoot;

	public Player(Main main, float posX, float posY) {
		super(main, posX, posY);

		ship = ImageLoader.loadImage("/battleship.png");
		shoot = new AudioPlayer("/sounds/shoot4.wav");
		if (shoot != null) shoot.setVolume(-10f);
		
		this.addComponent(new Collider());

		attackDelay.terminate();

		width = 24;
		height = 24;
	}

	@Override
	public void update() {
		updateComponents();
		updatePowerups();
		input();

		for (Projectile projectile : projectiles)
			if (projectile.isDead)
				toRemoveProj.add(projectile);
			else
				projectile.update();
		projectiles.removeAll(toRemoveProj);
		
		if (getPowerup(Effect.SHOOTFAST) != null) {
			attackDelay.setLength(100);
		} else {
			attackDelay.setLength(300);
		}
		
		if (getPowerup(Effect.TRISHOT) != null) {
			triShot = true;
		} else {
			triShot = false;
		}
		
		if (getPowerup(Effect.SHOOTFAR) != null) {
			for (Projectile proj : projectiles) {
				proj.setLife(160);
			}
		}
		
		powerups.removeAll(toRemovePow);
	}

	public void input() {
		this.velX = 0;
		this.velY = 0;

		if (main.getInput().isKey(KeyEvent.VK_UP)) {
			this.velY = -2;
		}
		if (main.getInput().isKey(KeyEvent.VK_LEFT)) {
			this.velX = -2;
		}
		if (main.getInput().isKey(KeyEvent.VK_DOWN)) {
			this.velY = 2;
		}
		if (main.getInput().isKey(KeyEvent.VK_RIGHT)) {
			this.velX = 2;
		}

		if (main.getInput().isKey(KeyEvent.VK_SPACE)) {
			attack();
		}

		if (posX >= 380)
			posX = 378;
		else if (posX <= 2)
			posX = 3;
		else
			posX += velX * Time.getDelta();
		if (posY >= 565)
			posY = 564;
		else if (posY <= 0)
			posY = 1;
		else
			posY += velY * Time.getDelta();
	}

	private void attack() {
		if (attackDelay.isOver()) {
			projectiles.add(new NormalProjectile(main, posX + 4, posY - 4));
			projectiles.add(new NormalProjectile(main, posX + 20, posY - 4));
			if (triShot) projectiles.add(new NormalProjectile(main, posX + width - 12, posY - 4));
			shoot.play();
			attackDelay.restart();
		}
		for (Projectile projectile : projectiles) {
			if (projectile.isDead)
				toRemoveProj.add(projectile);
			else
				projectile.setVelY(-3f);
		}
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(ship, (int) posX, (int) posY, null);

		for (Projectile projectile : projectiles) {
			projectile.render(graphics);
		}
	}

	@Override
	public void componentEvent(String tag, GameObject object) {
		
	}

}
