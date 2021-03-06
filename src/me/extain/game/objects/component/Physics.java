package me.extain.game.objects.component;

import java.util.ArrayList;

public class Physics {

	private ArrayList<Collider> objects = new ArrayList<Collider>();

	public void update() {
		for (int i = 0; i < objects.size(); i++) {
			for (int j = i + 1; j < objects.size(); j++) {
				Collider c0 = objects.get(i);
				Collider c1 = objects.get(j);

				if (Math.abs(c0.getX() - c1.getX()) < c0.getHalfWidth() + c1.getHalfWidth()) {
					if (Math.abs(c0.getY() - c1.getY()) < c0.getHalfHeight() + c1.getHalfHeight()) {
						c0.collision(c1.getObject());
						c1.collision(c0.getObject());
					}
				}
			}
		}

		objects.clear();
	}

	public void addCollider(Collider collider) {
		objects.add(collider);
	}

}
