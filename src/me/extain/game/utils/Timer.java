package me.extain.game.utils;

public class Timer {
	
	private int duration;
	private long endTime = 0;
	
	public Timer(int duration) {
		this.duration = duration;
	}
	
	public void start() {
		long time = Time.getTime();
		endTime = time + duration * 1000000L;
	}
	
	public void stop() {
		endTime = 0;
	}
	
	public boolean finished() {
		return Time.getTime() >= endTime;
	}
	
	public int getLength() {
		return duration;
	}
	
	public void setLength(float duration) {
		this.duration = (int) duration;
	}
}
