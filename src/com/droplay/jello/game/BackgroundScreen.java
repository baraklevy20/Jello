package com.droplay.jello.game;

import com.droplay.jello.JelloContext;
import com.droplay.jello.JelloScreen;
import com.droplay.jello.JelloResources;
import com.droplay.jello.game.objects.Tile;
import com.droplay.jello.game.objects.TileType;

public class BackgroundScreen extends JelloScreen {
	private static final long serialVersionUID = 1627796124419632083L;
	private static int numberOfScreens;
	
	public BackgroundScreen(JelloContext context, float scrollingSpeed) {
		super(context, scrollingSpeed);
		this.screenIndex = numberOfScreens++;
		randomizeScreen();
	}

	public boolean isOver() {
		if (screenIndex == 0)
			return distanceCounter + getRealScrollingSpeed() >= width * WIDTHS_PER_SCREEN;
		else
			return distanceCounter + getRealScrollingSpeed() >= width * WIDTHS_PER_SCREEN * 2;	
	}
	
	@Override
	public void randomizeScreen() {
		float space = JelloResources.HOLE.getWidth();
		tiles = new Tile[(int)Math.ceil(width / space)];
		
		int tempRandom;
		
		// Set everything to empty at first
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(this, TileType.EMPTY, (int)(space * i) + (screenIndex == 0 ? 0 : width - getRealScrollingSpeed()));
			// Generates trees and plants(the first one and last one can't have trees)
			if (i != 0 && i != tiles.length - 1) {
				if ((int)(Math.random() * 0) <= 2) {
					tiles[i].setType(TileType.TREE);
					tempRandom = (int)(Math.random() * 3);
					tiles[i].setSubType(tempRandom);
				}
			}				
		}
	}

	public static void resetNumberOfScreens() {
		BackgroundScreen.numberOfScreens = 0;
	}
}
