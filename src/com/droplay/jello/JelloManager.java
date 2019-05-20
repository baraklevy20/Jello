package com.droplay.jello;

import android.view.MotionEvent;

public abstract class JelloManager {
	
	protected GameLoop gameLoop;
	protected JelloContext context;
	
	public static final float CLOUDS_SPEED = 1.5f;
	public static final float BACKGROUND_SPEED = 3;
	public static final float FOREGROUND_SPEED = 9;
	public static final float CLOUDS_WIDTH = 3;
	
	public abstract void initialize();
	public abstract void onPause();
	public abstract void onResume();
	public abstract void progressGame();
	public abstract void handleTouch(MotionEvent event);
	
	public JelloManager(JelloContext context) {
		this.context = context;
		this.gameLoop = new GameLoop(context);
	}
	
	public GameLoop getGameLoop() {
		return gameLoop;
	}
}
