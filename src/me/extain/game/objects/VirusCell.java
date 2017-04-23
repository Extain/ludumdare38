package me.extain.game.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import me.extain.game.Main;
import me.extain.game.graphics.image.ImageLoader;
import me.extain.game.objects.component.Collider;
import me.extain.game.objects.projectile.Projectile;
import me.extain.game.state.GameState;
import me.extain.game.utils.Time;
import me.extain.game.utils.Utils;

public class VirusCell extends GameObject {

	private BloodCell target;

	private int latchTimer;

	private boolean collidedRight, collidedLeft;

	private BufferedImage cell;

	private enum states {
		LATCH, LOOKING, HUNTING
	}

	private states curState;

	private GameState state;

	public VirusCell(Main main, GameState state, float posX, float posY) {
		super(main, posX, posY);
		this.state = state;

		cell = ImageLoader.loadImage("/virus-cell.png");

		this.addComponent(new Collider());
		width = 24;
		height = 24;

		curState = states.LOOKING;
	}

	@Override
	public void update() {
		velX = 0;
		velY = 0;
		updateComponents();
		handleAI();

		collidedLeft = false;
		collidedRight = false;

		if (curState == states.LATCH)
			latchTimer++;

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

	private void handleAI() {
		switch (curState) {
		case LATCH: {
			latch();
			break;
		}
		case HUNTING: {
			chase();
			break;
		}
		case LOOKING: {
			look();
			break;
		}
		}
	}

	private void latch() {
		Random random = new Random();
		if (latchTimer >= random.nextInt(200) + 50) {

			target.turnToVirus();
			target = null;
			latchTimer = 0;
			curState = states.LOOKING;
		}
	}

	private void look() {
		ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
		ArrayList<GameObject> sight = state.sphereCollide(posX, posY, 128);
		for (int i = 0; i < sight.size(); i++) {
			BloodCell bc = (BloodCell) sight.get(i);
			if (target == null) {
				target = (BloodCell) bc;
				toRemove.add(bc);
				curState = states.HUNTING;
			}
		}
		sight.removeAll(toRemove);

		Random random = new Random();

		velX = random.nextInt(2);
		velY = random.nextInt(2);
	}

	private void chase() {
		float speedX = (target.getPosX() - posY);
		float speedY = (target.getPosY() - posY);

		float maxSpeed = 1f;

		if (speedX > maxSpeed)
			speedX = maxSpeed;
		if (speedX < -maxSpeed)
			speedX = -maxSpeed;
		if (speedY > maxSpeed)
			speedY = maxSpeed;
		if (speedY < -maxSpeed)
			speedY = -maxSpeed;

		velX = speedX;
		velY = speedY;

		if (Utils.dist(posX, posY, target.getPosX(), target.getPosY()) <= 20f) {
			curState = states.LATCH;
		}
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(cell, (int) posX, (int) posY, null);
	}

	@Override
	public void componentEvent(String tag, GameObject object) {
		if (object instanceof BloodCell) {
			target = (BloodCell) object;
			curState = states.LATCH;
		}
	}

	public void setState(states state) {
		this.curState = state;
	}

}
