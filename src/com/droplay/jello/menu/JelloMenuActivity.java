package com.droplay.jello.menu;

import android.app.Activity;
import android.content.Intent;

import com.droplay.jello.CloudsScreen;
import com.droplay.jello.JelloActivity;
import com.droplay.jello.menu.JelloMenuManager.ButtonState;

public class JelloMenuActivity extends JelloActivity {
	@Override
	public void setManagerAndGraphics() {
		graphicsView = new JelloMenuGraphics(context);
		gameManager = new JelloMenuManager(context);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == GAME_ID && resultCode == Activity.RESULT_OK) {
			((JelloMenuManager)gameManager).setCurrentClouds((CloudsScreen)data.getSerializableExtra(JelloActivity.KEY_CURRENT_CLOUDS));
			((JelloMenuManager)gameManager).setNextClouds((CloudsScreen)data.getSerializableExtra(JelloActivity.KEY_NEXT_CLOUDS));
		}
		
		if (requestCode == LEADERSBOARD_ID) {
			((JelloMenuManager)gameManager).setButtonState(ButtonState.NOTHING_PRESSED);
		}
	}
}
