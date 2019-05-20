package com.droplay.jello.game.objects;

import java.io.Serializable;

import com.droplay.jello.ProcessGameListener;

public abstract class MovableGameObject extends GameObject implements ProcessGameListener, Serializable {
	private static final long serialVersionUID = 4062909351318609408L;

	public MovableGameObject() {
		super();
	}
	
	public MovableGameObject(int x, int y) {
		super(x, y);
	}
}
