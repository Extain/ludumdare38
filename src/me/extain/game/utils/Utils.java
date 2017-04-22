package me.extain.game.utils;

import me.extain.game.objects.GameObject;

public class Utils {
	
	public static boolean lineOfSight(GameObject go1, GameObject go2) {
		return true;
	}
	
	public static double dist(float x1, float y1, float x2, float y2) {
		double x = x2 - x1;
		double y = y2 - y1;
		
		return (float)Math.sqrt(x * x) + (y * y);
	}

}
