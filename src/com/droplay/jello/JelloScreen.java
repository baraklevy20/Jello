package com.droplay.jello;

import java.io.Serializable;

import com.droplay.jello.game.objects.MovableGameObject;
import com.droplay.jello.game.objects.Tile;

public abstract class JelloScreen implements ProcessGameListener, Serializable {
	private static final long serialVersionUID = 3243130511511519813L;
	protected Tile[] tiles;
	protected int distanceCounter;
	protected int screenIndex;
	protected final float WIDTHS_PER_SCREEN;
	protected static int width, height;
	protected static int holeWidth;
	protected float scrollingSpeed;
	
	public float getScrollingSpeed() {
		return scrollingSpeed;
	}
	
	public Tile[] getTiles() {
		return tiles;
	}

	public void setScrollingSpeed(float scrollingSpeed) {
		this.scrollingSpeed = scrollingSpeed;
	}
	
	public int getRealScrollingSpeed() {
		return (int)(scrollingSpeed * holeWidth / 96);
	}
	
	public JelloScreen(JelloContext context, float scrollingSpeed) {
		this(context, scrollingSpeed, 1);
	}
	
	public JelloScreen(JelloContext context, float scrollingSpeed, final float WIDTHS_PER_SCREEN) {
		this.WIDTHS_PER_SCREEN = WIDTHS_PER_SCREEN;
		
		this.scrollingSpeed = scrollingSpeed;
		JelloScreen.width = (int)context.getGraphics().getWidth();
		JelloScreen.height = (int)context.getGraphics().getHeight();
		JelloScreen.holeWidth = JelloResources.HOLE.getWidth();
	}
	
	public abstract void randomizeScreen();
	
	@Override
	public void onGameProgress() {
		distanceCounter += getRealScrollingSpeed();
		
		for (int i = 0; i < tiles.length; i++)
			if (tiles[i] instanceof MovableGameObject) ((MovableGameObject)tiles[i]).onGameProgress();
	}
}
