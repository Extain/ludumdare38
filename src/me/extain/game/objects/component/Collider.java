package me.extain.game.objects.component;

import java.awt.Color;
import java.awt.Graphics2D;

import me.extain.game.Main;
import me.extain.game.objects.GameObject;

public class Collider extends Component {
	
	private GameObject object;
	private float x, y, hW, hH;
	
	public Collider() {
		setTag("Collider");
	}
	
	public void update(Main main, GameObject object) {
		if (this.object == null) this.object = object;
		hW = object.getWidth() / 2;
		hH = object.getHeight() / 2;
		x = object.getPosX() + hW;
		y = object.getPosY() + hH;
		main.getPhysics().addCollider(this);
	}
	
	public void render(Graphics2D graphics) {
		graphics.setColor(Color.BLUE);
		graphics.drawRect((int) x, (int) y, (int) hW, (int) hH);
	}
	
	public void collision(GameObject object) {
		this.object.componentEvent(tag, object);
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getHalfWidth() { return hW; }
	public float getHalfHeight() { return hH; }
	
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setHalfWidth(float hW) { this.hW = hW; }
	public void setHalfHeight(float hH) { this.hH = hH; }
	
	public GameObject getObject() { return object; }
	
	public void setObject(GameObject object) { this.object = object; }

}
