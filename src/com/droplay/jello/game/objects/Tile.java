package com.droplay.jello.game.objects;

import java.io.Serializable;

import com.droplay.jello.JelloScreen;

public class Tile extends MovableGameObject implements Serializable {
	private static final long serialVersionUID = -5739907351002421778L;
	protected JelloScreen screen;
	protected int subType;
	
	public Tile(JelloScreen screen, TileType type, int x) {
		this(screen, type, x, 0);
	}
	
	public Tile(JelloScreen screen, TileType type, int x, int y) {
		this.screen = screen;
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public TileType getType() {
		return type;
	}
	
	public void setType(TileType type) {
		this.type = type;
	}
	
	public int getSubType() {
		return subType;
	}
	
	public void setSubType(int subType) {
		this.subType = subType;
	}
	
	@Override
	public void onGameProgress() {
		x -= screen.getRealScrollingSpeed();
	}
}