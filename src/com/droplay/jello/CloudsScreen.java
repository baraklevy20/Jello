package com.droplay.jello;

import java.io.Serializable;

import com.droplay.jello.game.objects.Tile;
import com.droplay.jello.game.objects.TileType;

public class CloudsScreen extends JelloScreen implements Serializable {
	private static final long serialVersionUID = 6050941625832432535L;
	private static int numberOfScreens;
	
	public boolean isOver() {
		if (screenIndex == 0)
			return distanceCounter + getRealScrollingSpeed() >= width * WIDTHS_PER_SCREEN;
		else
			return distanceCounter + getRealScrollingSpeed() >= width * WIDTHS_PER_SCREEN * 2;	
	}

	public CloudsScreen(JelloContext context, float scrollingSpeed) {
		super(context, scrollingSpeed, JelloManager.CLOUDS_WIDTH);
		this.screenIndex = numberOfScreens++;
		randomizeScreen();
	}
	
	public void randomizeScreen() {
		tiles = new Tile[6];
		int space = (height - JelloResources.GROUND.getHeight() - height * 3 / 100 - JelloResources.GO_BUTTON.getHeight() * 2) / tiles.length;
		int tempRandom;
		
		for (int i = 0; i < tiles.length; i++) {
			tempRandom = (int)(Math.random() * JelloResources.CLOUDS.length);
			tiles[i] = new Tile(this, TileType.CLOUD,
					(int)(Math.random() * (width * WIDTHS_PER_SCREEN - JelloResources.CLOUDS[tempRandom].getWidth()))
					+ (screenIndex == 0 ? 0 : (int)(WIDTHS_PER_SCREEN * width) - getRealScrollingSpeed()),
					space * i + (int)(Math.random() * (space - JelloResources.CLOUDS[tempRandom].getHeight())));
			tiles[i].setSubType(tempRandom);
		}
	}
}
