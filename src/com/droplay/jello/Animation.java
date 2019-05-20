package com.droplay.jello;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Animation implements Serializable {
	private static final long serialVersionUID = 2102538369231483243L;
	
	private int currentFrame;
	private Bitmap[] frames;
	private final float INTERVAL;
	private int currentInterval;
	private boolean isLoop;
	private long oldTime;
	
	public Animation(final float INTERVAL, boolean isLoop, Bitmap... frames) {
		this.frames = frames;
		this.INTERVAL = INTERVAL;
		this.isLoop = isLoop;
	}
	
	public int getNumberOfFrames() {
		return frames.length;
	}
	
	public void resetAnimation() {
		currentFrame = 0;
		currentInterval = 0;
	}
	
	public int getCurrentFrameIndex() {
		return currentFrame;
	}
	
	public Bitmap getCurrentFrame() {
		Bitmap bitmap = frames[currentFrame];
		long newTime = System.currentTimeMillis();
		
		if (oldTime != 0)
			currentInterval += newTime - oldTime;
		
		oldTime = newTime;
		
		if (currentInterval / 1000.0 > INTERVAL) {
			if (isLoop)
				currentFrame = (currentFrame + 1) % frames.length;
			else if (currentFrame < frames.length - 1)
				currentFrame++;
			
			currentInterval = 0;
		}
		
		return bitmap;
	}
}