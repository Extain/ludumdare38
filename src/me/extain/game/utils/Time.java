package me.extain.game.utils;

public class Time {
	
	private static final float DAMPING = 18e6f;
	
	private static long curTime;
	private static long lastTime;
	
	public static long getTime() {
		return System.nanoTime();
	}
	
	public static float getDelta() {
		return (curTime - lastTime) / DAMPING;
	}
	
	public static void update() {
		lastTime = curTime;
		curTime = getTime();
	}
	
	public static void init() {
		lastTime = getTime();
		curTime = getTime();
	}

}
