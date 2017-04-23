package me.extain.game.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import me.extain.game.Main;
import me.extain.game.graphics.image.ImageLoader;
import me.extain.game.objects.BloodCell;
import me.extain.game.objects.GameObject;
import me.extain.game.objects.VirusCell;
import me.extain.game.objects.Wall;
import me.extain.game.objects.player.Player;
import me.extain.game.objects.powerup.FastShoot;
import me.extain.game.objects.powerup.Powerup;
import me.extain.game.utils.Delay;
import me.extain.game.utils.Utils;

public class GameState extends State {

	private Player player;
	private Wall wall1, wall2;
	private VirusCell virusTest;

	private ArrayList<VirusCell> viruses = new ArrayList<VirusCell>();
	private ArrayList<GameObject> bloodCells = new ArrayList<GameObject>();
	private ArrayList<Powerup> powerups = new ArrayList<Powerup>();

	private ArrayList<GameObject> toRemoveBlood = new ArrayList<GameObject>();
	private ArrayList<GameObject> toRemoveVirus = new ArrayList<GameObject>();
	private ArrayList<GameObject> toRemovePowerup = new ArrayList<GameObject>();

	private Delay virusSpawnDelay, bloodSpawnDelay, decreaseVirusSpawnTimer, powerupSpawnDelay;

	private BufferedImage background;
	
	private int virusSpawnTimer = 0;

	public GameState(Main main) {
		super(main);
		player = new Player(main, 50, 50);

		virusSpawnDelay = new Delay(1300);
		virusSpawnDelay.terminate();

		bloodSpawnDelay = new Delay(300);
		bloodSpawnDelay.terminate();
		
		powerupSpawnDelay = new Delay(1200);
		powerupSpawnDelay.terminate();
		
		background = ImageLoader.loadImage("/vein-background.png");
		
		decreaseVirusSpawnTimer = new Delay(400);
		decreaseVirusSpawnTimer.terminate();

		for (int i = 0; i < 100; i++) {
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
		if (bloodSpawnDelay.isOver()) {
			bloodCells.add(new BloodCell(main, this, (float) 15 + random.nextInt(300), (float) 10 + random.nextInt(500)));
			bloodSpawnDelay.restart();
		}
		
	}

	@Override
	public void update() {
		player.update();
		for (VirusCell virus : viruses) {
			if (virus.isDead)
				toRemoveVirus.add(virus);
			else
				virus.update();
		}
		
		for (Powerup pow : powerups) {
			if (pow.isDead)
				toRemovePowerup.add(pow);
			else
				pow.update();
		}
		
		if (powerupSpawnDelay.isOver()) {
			Random random = new Random();
			powerups.add(new FastShoot(main, random.nextInt(200), random.nextInt(400)));
			powerupSpawnDelay.restart();
		}

		if (virusSpawnDelay.isOver() && virusSpawnDelay.isActive()) {
			Random random = new Random();
			viruses.add(new VirusCell(main, this, random.nextInt(200), random.nextInt(400)));
			virusSpawnDelay.restart();
		}
		
		if (decreaseVirusSpawnTimer.isOver()) {
			if (virusSpawnDelay.getLength() != 600) 
				virusSpawnDelay.setLength(virusSpawnDelay.getLength() - 10);
			decreaseVirusSpawnTimer.restart();
		}

		for (GameObject bloodCell : bloodCells) {
			if (bloodCell.isInfected) {
				viruses.add(new VirusCell(main, this, bloodCell.getPosX(), bloodCell.getPosY()));
				toRemoveBlood.add(bloodCell);
			} else
				bloodCell.update();

		}
		bloodSpawn();

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
		
		for (Powerup pow : powerups)
			pow.render(graphics);

		player.render(graphics);

		graphics.setColor(Color.WHITE);
		graphics.drawString("Blood Cells: " + bloodCells.size(), 10, 40);
		graphics.drawString("Virus Cells: " + viruses.size(), 10, 70);
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
