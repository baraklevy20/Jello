package com.droplay.jello;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop implements Runnable {
	private Thread gameLoopThread;
	private boolean isRunning;
	private JelloContext context;
	private SurfaceHolder holder;
	
	public static final int FPS = 25;
	public static final int MAX_FRAMES_SKIPPED = 5;
	
	public GameLoop(JelloContext context) {
		this.context = context;
	}
	
	public void startGameLoop(SurfaceHolder holder) {
		this.holder = holder;
		isRunning = true;
		gameLoopThread = new Thread(this);
		gameLoopThread.start();
	}

	public void stopGameLoop() {
		if (isRunning) {
			isRunning = false;
		}
	}
	
	public Thread getThread() {
		return gameLoopThread;
	}
	
	public boolean isAlive() {
		return gameLoopThread.isAlive();
	}

	public void run() {
		long startTime, sleepTime;
		final JelloManager game = context.getManager();
		final JelloGraphics graphics = context.getGraphics();
		Canvas canvas;
		int framesSkipped;
		
		try {
			while (isRunning) {
				startTime = System.currentTimeMillis();
				game.progressGame();
				canvas = holder.lockCanvas();
				
				if (canvas != null) {
					graphics.doDraw(canvas);
					holder.unlockCanvasAndPost(canvas);
				}
				
				sleepTime = 1000 / FPS - (System.currentTimeMillis() - startTime);
				
				if (sleepTime > 0)
					Thread.sleep(sleepTime);
				else {
					framesSkipped = 0;
					
					while (sleepTime < 0 && framesSkipped++ < MAX_FRAMES_SKIPPED) {
						game.progressGame();
						sleepTime += 1000 / FPS;
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}