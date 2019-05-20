package com.droplay.jello.game.objects;

import java.io.Serializable;

public abstract class GameObject implements Serializable {
	private static final long serialVersionUID = -322792428173017861L;
	protected int x, y;
	protected TileType type;
	
	public GameObject() {
		this(0, 0);
	}
	
	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
		this.type = TileType.EMPTY;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setType(TileType type) {
		this.type = type;
	}
	
	public TileType getType() {
		return type;
	}
}
