package me.extain.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import me.extain.game.Main;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {
	
	private boolean[] keys = new boolean[256];
    private boolean[] keysLast = new boolean[256];

    private boolean[] buttons = new boolean[5];
    private boolean[] buttonsLast = new boolean[5];

    private int mouseX, mouseY;

    public InputHandler(Main main) {
    	main.getWindow().addKeyListener(this);
        main.getWindow().addMouseListener(this);
        main.getWindow().addMouseMotionListener(this);
    }

    public void update() {
        keysLast = keys.clone();
        buttonsLast = buttons.clone();
    }

    public boolean isKey(int keyCode) { return keys[keyCode]; }
    public boolean isKeyPressed(int keyCode) { return keys[keyCode] && !keysLast[keyCode]; }
    public boolean isKeyReleased(int keyCode) { return !keys[keyCode] && keysLast[keyCode];}
    public boolean isButton(int button) { return buttons[button]; }
    public boolean isButtonPressed(int button) { return buttons[button] && !buttonsLast[button]; }
    public boolean isButtonReleased(int button) { return !buttons[button] && buttonsLast[button]; }

    public void mouseDragged(MouseEvent e) {
        mouseX = (int)(e.getX());
        mouseY = (int)(e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = (int)(e.getX());
        mouseY = (int)(e.getY());
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) { buttons[e.getButton()] = true; }
    public void mouseReleased(MouseEvent e) { buttons[e.getButton()] = false; }
    public void keyPressed(KeyEvent e) { keys[e.getKeyCode()] = true; }
    public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }

    public void keyTyped(KeyEvent e) {}

    public int getMouseX() { return mouseX; }
    public int getMouseY() { return mouseY; }
	
}
