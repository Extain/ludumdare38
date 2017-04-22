package me.extain.game.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	private Graphics2D graphics;
	private BufferStrategy bs;
	
	private String title;
	private int width, height;
	
	public Window(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		createBufferStrategy(2);
		bs = getBufferStrategy();
	}
	
	public void update() {
		graphics = (Graphics2D) bs.getDrawGraphics();
		graphics.setColor(new Color(229, 43, 80));
		graphics.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void render() {
		graphics.dispose();
		bs.show();
	}
	
	public Graphics2D getGraphics() {
		return graphics;
	}
	
	public void cleanUp() {
		graphics.dispose();
		bs.dispose();
		dispose();
	}
}
