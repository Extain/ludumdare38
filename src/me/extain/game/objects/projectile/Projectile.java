package me.extain.game.objects.projectile;

import java.util.ArrayList;

import me.extain.game.Main;
import me.extain.game.audio.AudioPlayer;
import me.extain.game.objects.GameObject;
import me.extain.game.objects.VirusCell;
import me.extain.game.objects.component.Collider;

public abstract class Projectile extends GameObject {
	
	protected ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	protected float life;
	
	protected AudioPlayer hurt;

	public Projectile(Main main, float posX, float posY) {
		super(main, posX, posY);
		this.addComponent(new Collider());
		hurt = new AudioPlayer("/sounds/hurt.wav");
		this.life = 80;
	}
	
	public void update() {
		if (posY <= 0) this.isDead = true;
		
		life--;
		
		if (life == 0) this.isDead = true;
		
		this.updateComponents();
	}
	
	public void setVelY(float velY) {
		this.velY = velY;
	}

	public void componentEvent(String tag, GameObject object) {
		if (object instanceof VirusCell) {
			object.isDead = true;
			hurt.play();
			this.isDead = true;
		}
	}
	
	public void setLife(float life) {
		this.life = life;
	}
}
