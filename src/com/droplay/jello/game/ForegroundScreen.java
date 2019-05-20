package com.droplay.jello.game;

import com.droplay.jello.JelloContext;
import com.droplay.jello.JelloScreen;
import com.droplay.jello.JelloResources;
import com.droplay.jello.game.objects.Hole;
import com.droplay.jello.game.objects.Tile;
import com.droplay.jello.game.objects.TileType;

public class ForegroundScreen extends JelloScreen {
	private static final long serialVersionUID = -2861242302986559802L;
	private Tile[] plants;
	protected JelloContext context;
	private static int nextHoleIndex;
	public static final int MIN_HOLES_SPACE = 4;
	public static final int MAX_HOLES_SPACE = 6;
	private static int numberOfScreens = 0;
	private boolean hasHoles;
	
	public ForegroundScreen(JelloContext context, float scrollingSpeed) {
		this(context, scrollingSpeed, true);
	}
	
	public ForegroundScreen(JelloContext context, float scrollingSpeed, boolean hasHoles) {
		super(context, scrollingSpeed);
		this.context = context;
		this.screenIndex = numberOfScreens++;
		this.hasHoles = hasHoles;
		randomizeScreen();
	}
	
	public static void resetNumberOfScreens() {
		numberOfScreens = 0;
	}
	
	public boolean isOver() {
		if (screenIndex == 0)
			return distanceCounter + getRealScrollingSpeed() >= width * WIDTHS_PER_SCREEN;
		else
			return distanceCounter + getRealScrollingSpeed() >= width * WIDTHS_PER_SCREEN * 2;	
	}
	
	public Tile[] getPlants() {
		return plants;
	}
	
	@Override
	public void onGameProgress() {
		super.onGameProgress();
		
		for (int i = 0; i < plants.length; i++)
			plants[i].onGameProgress();
	}
	
	public void randomizeScreen() {
		float space = JelloResources.HOLE.getWidth();
		tiles = new Tile[(int)Math.ceil(width / space)];
		plants = new Tile[(int)Math.ceil(width / space)];
		
		int tempRandom;
		
		// Set everything to ground at first
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(this, TileType.GROUND, (int)(space * i) + (screenIndex == 0 ? 0 : width - getRealScrollingSpeed()));
			plants[i] = new Tile(this, TileType.EMPTY, (int)(space * i) + (screenIndex == 0 ? 0 : width - getRealScrollingSpeed()));
			
			// Generates plants(the first one and last one can't have plants)
			if (i != 0 && i != tiles.length - 1) {
				if ((int)(Math.random() * 3) == 0) {
					tempRandom = (int)(Math.random() * 4);
					plants[i].setType(TileType.PLANT);
					plants[i].setSubType(tempRandom);
				}
			}
			
		}
		
		// Random holes
		if (hasHoles) {
			if (screenIndex == 1) {
				plants[2].setType(TileType.EMPTY);
				tiles[2] = null;
				tiles[2] = new Hole(context, this, (int)(space * 2) + (screenIndex == 0 ? 0 : width - getRealScrollingSpeed()));
				nextHoleIndex = 2 + MIN_HOLES_SPACE + (int)(Math.random() * (MAX_HOLES_SPACE - MIN_HOLES_SPACE + 1)) + 1;
			}
			else if (screenIndex > 1) {
				if (nextHoleIndex >= tiles.length - 1) {
					nextHoleIndex -= tiles.length;
					
					if (nextHoleIndex == 1)
						nextHoleIndex = 2;
					
					if (nextHoleIndex <= 0)
						nextHoleIndex = 1;
				}
				
				if (nextHoleIndex > 0 && nextHoleIndex < tiles.length - 1) {
					tiles[nextHoleIndex].setType(TileType.HOLE);
					tiles[nextHoleIndex] = null;
					tiles[nextHoleIndex] = new Hole(context, this, (int)(space * nextHoleIndex) + (screenIndex == 0 ? 0 : width - getRealScrollingSpeed()));
					plants[nextHoleIndex].setType(TileType.EMPTY);
					nextHoleIndex += MIN_HOLES_SPACE + (int)(Math.random() * (MAX_HOLES_SPACE - MIN_HOLES_SPACE + 1)) + 1;
				}
			}
		}
	}
}
