package me.extain.game.objects.powerup;

import me.extain.game.objects.effects.Effect;
import me.extain.game.utils.Timer;

public class Powerup {
	private Effect effect;
	
	private Timer timer;
	
	public Powerup(Effect effect, int duration) {
		this.effect = effect;
		timer = new Timer(duration);
		timer.start();
	}
	
	public Effect getEffect() {
		return effect;
	}
	
	public void update() {
		
	}
	
	public boolean shouldRemove() {
		return timer.finished();
	}
}
