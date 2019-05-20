package com.droplay.jello.menu;

import java.io.Serializable;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.view.MotionEvent;

import com.droplay.jello.CloudsScreen;
import com.droplay.jello.JelloActivity;
import com.droplay.jello.JelloContext;
import com.droplay.jello.JelloManager;
import com.droplay.jello.JelloResources;
import com.droplay.jello.R;
import com.droplay.jello.game.BackgroundScreen;
import com.droplay.jello.game.ForegroundScreen;
import com.droplay.jello.game.JelloGameActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class JelloMenuManager extends JelloManager {
	private CloudsScreen currentClouds, nextClouds;
	private ForegroundScreen currentScreen, nextScreen;
	private BackgroundScreen currentTrees, nextTrees;
	private ButtonState buttonState = ButtonState.NOTHING_PRESSED;
	
	public enum ButtonState {
		NOTHING_PRESSED,
		GO_PRESSED,
		SCORES_PRESSED,
		RATE_US_PRESSED
	}
	
	public ButtonState getButtonState() {
		return buttonState;
	}
	
	public void setButtonState(ButtonState buttonState) {
		this.buttonState = buttonState;
	}
	
	public JelloMenuManager(JelloContext context) {
		super(context);
	}
	
	public CloudsScreen getCurrentClouds() {
		return currentClouds;
	}
	
	public CloudsScreen getNextClouds() {
		return nextClouds;
	}
	
	public void setCurrentClouds(CloudsScreen currentClouds) {
		this.currentClouds = currentClouds;
	}
	
	public void setNextClouds(CloudsScreen nextClouds) {
		this.nextClouds = nextClouds;
	}
	
	public ForegroundScreen getCurrentScreen() {
		return currentScreen;
	}

	public ForegroundScreen getNextScreen() {
		return nextScreen;
	}
	
	public BackgroundScreen getCurrentTrees() {
		return currentTrees;
	}

	public BackgroundScreen getNextTrees() {
		return nextTrees;
	}

	@Override
	public void initialize() {
		if (currentClouds == null)
			currentClouds = new CloudsScreen(context, CLOUDS_SPEED);
		
		if (nextClouds == null)
			nextClouds = new CloudsScreen(context, CLOUDS_SPEED);
		
		if (currentScreen == null) {
			ForegroundScreen.resetNumberOfScreens();
			currentScreen = new ForegroundScreen(context, JelloManager.FOREGROUND_SPEED, false);
		}
		
		if (nextScreen == null)
			nextScreen = new ForegroundScreen(context, JelloManager.FOREGROUND_SPEED, false);
		
		if (currentTrees == null) {
			BackgroundScreen.resetNumberOfScreens();
			currentTrees = new BackgroundScreen(context, JelloManager.BACKGROUND_SPEED);
		}
		
		if (nextTrees == null)
			nextTrees = new BackgroundScreen(context, JelloManager.BACKGROUND_SPEED);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
//		gameLoop.stopGameLoop();
	}

	@Override
	public void onResume() {
//		currentScreen = new ForegroundScreen(context, JelloManager.FOREGROUND_SPEED, false);
//		nextScreen = new ForegroundScreen(context, JelloManager.FOREGROUND_SPEED, false);
	}

	@Override
	public void progressGame() {
		// Progress screens
		currentClouds.onGameProgress();
		nextClouds.onGameProgress();
		
		if (currentClouds.isOver()) {
			currentClouds = nextClouds;
			nextClouds = new CloudsScreen(context, JelloManager.CLOUDS_SPEED);
		}
		
		currentScreen.onGameProgress();
		nextScreen.onGameProgress();
		
		if (currentScreen.isOver()) {
			currentScreen = nextScreen;
			nextScreen = new ForegroundScreen(context, JelloManager.FOREGROUND_SPEED, false);
		}
		
		currentTrees.onGameProgress();
		nextTrees.onGameProgress();
		
		if (currentTrees.isOver()) {
			currentTrees = nextTrees;
			nextTrees = new BackgroundScreen(context, JelloManager.BACKGROUND_SPEED);
		}
	}

	@Override
	public void handleTouch(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		int width = context.getGraphics().getWidth();
		int height = context.getGraphics().getHeight();
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (x >= width / 2 - JelloResources.GO_BUTTON.getWidth() / 2 && x <= width / 2 + JelloResources.GO_BUTTON.getWidth() / 2 &&
					y >= height / 2 + JelloResources.GO_BUTTON.getHeight() / 2  - JelloResources.GO_BUTTON.getHeight() * 3 / 2 && y <= height / 2 + 1.5 * JelloResources.GO_BUTTON.getHeight()  - JelloResources.GO_BUTTON.getHeight() * 3 / 2) {
				if (buttonState == ButtonState.NOTHING_PRESSED) {
					playSound();
					buttonState = ButtonState.GO_PRESSED;
				}
			}
			
			if (x >= width / 2 - JelloResources.GO_BUTTON.getWidth() / 2 && x <= width / 2 + JelloResources.GO_BUTTON.getWidth() / 2 &&
					y >= height / 2 + JelloResources.GO_BUTTON.getHeight() / 2 && y <= height / 2 + 1.5 * JelloResources.GO_BUTTON.getHeight()) {
				if (buttonState == ButtonState.NOTHING_PRESSED) {
					playSound();
					buttonState = ButtonState.SCORES_PRESSED;
				}
			}
			
			JelloResources.VOLTER_GOLDFISH_PAINT.setTextSize(context.getActivity().getResources().getDimension(R.dimen.end_game_go));
			JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFFE64B68);
			Rect bounds = new Rect();
			String currentString = "Rate us";
			JelloResources.VOLTER_GOLDFISH_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds);
			
			if (x >= width / 14 && x <= width / 14 + bounds.right &&
					y >= width / 10 + bounds.top && y <= width / 10) {
				if (buttonState == ButtonState.NOTHING_PRESSED) {
					playSound();
					buttonState = ButtonState.RATE_US_PRESSED;
				}
			}
		}
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			switch (buttonState) {
			case GO_PRESSED:
				gameLoop.stopGameLoop();
				Intent intent = new Intent(context.getActivity(), JelloGameActivity.class);
				intent.putExtra(JelloActivity.KEY_CURRENT_CLOUDS, (Serializable)currentClouds);
				intent.putExtra(JelloActivity.KEY_NEXT_CLOUDS, (Serializable)nextClouds);
				context.getActivity().startActivityForResult(intent, JelloMenuActivity.GAME_ID);
				buttonState = ButtonState.NOTHING_PRESSED;
				break;
			case SCORES_PRESSED:
				if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context.getActivity()) == ConnectionResult.SERVICE_MISSING)
		        	buttonState = ButtonState.NOTHING_PRESSED;
				context.getActivity().showLeaderboard();
				break;
			case RATE_US_PRESSED:
				Uri uri = Uri.parse("market://details?id=" + context.getActivity().getPackageName());
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				try {
					context.getActivity().startActivity(goToMarket);
				} catch (ActivityNotFoundException e) {
					context.getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getActivity().getPackageName())));
				}
				buttonState = ButtonState.NOTHING_PRESSED;
				break;
			case NOTHING_PRESSED:
			}
		}
	}
	
	public void playSound() {
//		MediaPlayer player = MediaPlayer.create(context.getActivity(), R.raw.beep);
//		player.start();
		MediaPlayer player = MediaPlayer.create(context.getActivity(), R.raw.button);
		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {
		    public void onCompletion(MediaPlayer mp) {
		        mp.release();
		    };
		});
	}
}
