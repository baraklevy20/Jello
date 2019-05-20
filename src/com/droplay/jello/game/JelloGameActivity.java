package com.droplay.jello.game;

import com.droplay.jello.JelloActivity;

public class JelloGameActivity extends JelloActivity {
	@Override
	public void setManagerAndGraphics() {
		graphicsView = new JelloGameGraphics(context);
		gameManager = new JelloGameManager(context);
	}
}