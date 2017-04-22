package me.extain.game;

import me.extain.game.display.Window;
import me.extain.game.input.InputHandler;
import me.extain.game.objects.component.Physics;
import me.extain.game.state.GameState;
import me.extain.game.state.StateMachine;
import me.extain.game.utils.Time;

public class Main implements Runnable {
	
	private boolean running = false;
	
	private Window window;
	
	private Thread thread;
	
	private StateMachine stateMachine = new StateMachine();
	
	private InputHandler input;
	
	private Physics physics;
	
	public Main() {
		window = new Window("M.E.D.S", 400, 600);
		input = new InputHandler(this);
		physics = new Physics();
		
		thread = new Thread(this);
		running = true;
		
		thread.start();
	}
	
	private void init() {
		// Initialize the states
		Time.init();
		stateMachine.setState(new GameState(this));
	}
	
	public void run() {
		init();
		
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1e9 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				Time.update();
				stateMachine.peek().update();
				physics.update();
				updates++;
				delta--;
			}
			window.update();
			stateMachine.peek().render(window.getGraphics());
			window.render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				updates = 0;
				frames = 0;
			}
		}
		
		cleanUp();
	}
	
	public Physics getPhysics() {
		return physics;
	}
	
	public InputHandler getInput() {
		return input;
	}
	
	public Window getWindow() {
		return window;
	}
	
	public StateMachine getStateMachine() {
		return stateMachine;
	}
	
	private void cleanUp() {
		window.cleanUp();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
