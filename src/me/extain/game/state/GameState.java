package me.extain.game.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import me.extain.game.Main;
import me.extain.game.audio.AudioPlayer;
import me.extain.game.graphics.image.ImageLoader;
import me.extain.game.objects.BloodCell;
import me.extain.game.objects.GameObject;
import me.extain.game.objects.VirusCell;
import me.extain.game.objects.player.Player;
import me.extain.game.objects.powerup.FastShoot;
import me.extain.game.objects.powerup.PowerupPickup;
import me.extain.game.objects.powerup.ShootFarther;
import me.extain.game.objects.powerup.TriShot;
import me.extain.game.utils.Timer;
import me.extain.game.utils.Utils;

public class GameState extends State {

	private Player player;

	private ArrayList<VirusCell> viruses = new ArrayList<VirusCell>();
	private ArrayList<GameObject> bloodCells = new ArrayList<GameObject>();
	private ArrayList<PowerupPickup> powerups = new ArrayList<PowerupPickup>();

	private ArrayList<GameObject> toRemoveBlood = new ArrayList<GameObject>();
	private ArrayList<GameObject> toRemoveVirus = new ArrayList<GameObject>();
	private ArrayList<GameObject> toRemovePowerup = new ArrayList<GameObject>();

	private Timer virusSpawnDelay, bloodSpawnDelay, decreaseVirusSpawnTimer, powerupSpawnDelay;

	private int score = 0;

	private BufferedImage background;

	public GameState(Main main) {
		super(main);
		player = new Player(main, 50, 50);

		virusSpawnDelay = new Timer(1300);
		virusSpawnDelay.start();

		bloodSpawnDelay = new Timer(400);
		bloodSpawnDelay.start();

		powerupSpawnDelay = new Timer(1200);
		powerupSpawnDelay.start();

		background = ImageLoader.loadImage("/vein-background.png");

		decreaseVirusSpawnTimer = new Timer(400);
		decreaseVirusSpawnTimer.start();

		for (int i = 0; i < 300; i++) {
			Random random = new Random();
			bloodCells
					.add(new BloodCell(main, this, (float) 15 + random.nextInt(300), (float) 10 + random.nextInt(500)));
		}
	}

	@Override
	public void init() {

	}

	private void bloodSpawn() {
		Random random = new Random();
		if (bloodSpawnDelay.finished()) {

			bloodCells
					.add(new BloodCell(main, this, (float) 15 + random.nextInt(300), (float) 10 + random.nextInt(500)));
			bloodSpawnDelay.start();
		}

	}

	@Override
	public void update() {
		player.update();
		for (VirusCell virus : viruses) {
			if (virus.isDead) {
				toRemoveVirus.add(virus);
				score += 100;
			} else
				virus.update();
		}

		for (PowerupPickup pow : powerups) {
			if (pow.shouldRemove())
				toRemovePowerup.add(pow);
			else
				pow.update();
		}

		if (powerupSpawnDelay.finished()) {
			Random powRand = new Random();

			int effect = powRand.nextInt(1000);

			switch (effect) {
			case 10:

				powerups.add(new TriShot(main, powRand.nextInt(200), powRand.nextInt(400)));
				powerupSpawnDelay.start();
				effect = 0;
				break;
			case 30:
				powerups.add(new ShootFarther(main, powRand.nextInt(200), powRand.nextInt(400)));
				effect = 0;
				break;
			case 40:
				powerups.add(new FastShoot(main, powRand.nextInt(200), powRand.nextInt(400)));
				powerupSpawnDelay.start();
				effect = 0;
				break;
			}
		}
		
		if (virusSpawnDelay.finished()) {
			Random random = new Random();
			viruses.add(new VirusCell(main, this, random.nextInt(200), random.nextInt(400)));
			virusSpawnDelay.start();
		}

		if (decreaseVirusSpawnTimer.finished()) {
			if (virusSpawnDelay.getLength() != 50)
				virusSpawnDelay.setLength(virusSpawnDelay.getLength() - 10);

			if (bloodSpawnDelay.getLength() != 1600)
				bloodSpawnDelay.setLength(bloodSpawnDelay.getLength() + 50);

			decreaseVirusSpawnTimer.start();
		}

		for (GameObject bloodCell : bloodCells) {
			if (bloodCell.isInfected) {
				viruses.add(new VirusCell(main, this, bloodCell.getPosX(), bloodCell.getPosY()));
				toRemoveBlood.add(bloodCell);
			} else
				bloodCell.update();

		}
		bloodSpawn();

		if (bloodCells.size() == 0) {
			main.getStateMachine().setState(new DeathState(main, score));
		}

		bloodCells.removeAll(toRemoveBlood);
		viruses.removeAll(toRemoveVirus);
		powerups.removeAll(toRemovePowerup);
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(background, 0, 0, 400, 600, null);

		for (GameObject bloodCell : bloodCells) {
			bloodCell.render(graphics);
		}
		for (VirusCell virus : viruses)
			virus.render(graphics);

		for (PowerupPickup pow : powerups)
			pow.render(graphics);

		player.render(graphics);

		graphics.setColor(Color.WHITE);
		graphics.drawString("Score: " + score, 20, 50);
	}

	public ArrayList<GameObject> sphereCollide(float x, float y, float radius) {
		ArrayList<GameObject> res = new ArrayList<GameObject>();

		for (GameObject bc : bloodCells) {
			if (Utils.dist(bc.getPosX(), bc.getPosY(), x, y) < radius) {
				res.add(bc);
			}
		}
		return res;
	}

	public void addVirus(VirusCell virus) {
		viruses.add(virus);
	}

	public ArrayList<GameObject> getBloodCells() {
		return bloodCells;
	}

	public ArrayList<VirusCell> getVirusCells() {
		return viruses;
	}

}
