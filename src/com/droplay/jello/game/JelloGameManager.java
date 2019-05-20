package com.droplay.jello.game;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.MotionEvent;

import com.droplay.jello.CloudsScreen;
import com.droplay.jello.JelloActivity;
import com.droplay.jello.JelloContext;
import com.droplay.jello.JelloManager;
import com.droplay.jello.JelloResources;
import com.droplay.jello.R;
import com.droplay.jello.game.objects.Slime;
import com.droplay.jello.game.objects.State;

public class JelloGameManager extends JelloManager {
	private Slime slime;
	private ForegroundScreen currentForegroundScreen, nextForegroundScreen;
	private BackgroundScreen currentBackgroundScreen, nextBackgroundScreen;
	private CloudsScreen currentClouds, nextClouds;
	private boolean isGameStarted;
	private boolean isGameEnd;
	private int score, bestScore;
	private boolean isNewBestScore;
	private boolean isInitialized;
	private MediaPlayer player;
	private boolean isGoPressed;
	
	public boolean isGameStarted() {
		return isGameStarted;
	}
	
	public boolean isGameEnded() {
		return isGameEnd;
	}
	
	@Override
	public void initialize() {
		if (!isInitialized) {
			score = 0;
			isGameEnd = false;
			slime = new Slime(context);
			slime.setState(State.STANDING);
			slime.setX(context.getGraphics().getWidth() / 2 - (JelloResources.STAND_ANIMATION.getCurrentFrame().getWidth() - (int)context.getActivity().getResources().getDimension(R.dimen.slime_mergin)) / 2);
			slime.setY(((JelloGameGraphics)context.getGraphics()).getGroundY() - (JelloResources.STAND_ANIMATION.getCurrentFrame().getHeight() - (int)context.getActivity().getResources().getDimension(R.dimen.slime_mergin)));
			ForegroundScreen.resetNumberOfScreens();
			BackgroundScreen.resetNumberOfScreens();
			currentForegroundScreen = new ForegroundScreen(context, JelloManager.FOREGROUND_SPEED);
			nextForegroundScreen = new ForegroundScreen(context, JelloManager.FOREGROUND_SPEED);
			currentBackgroundScreen = new BackgroundScreen(context, JelloManager.BACKGROUND_SPEED);
			nextBackgroundScreen = new BackgroundScreen(context, JelloManager.BACKGROUND_SPEED);
			context.getActivity().readParameters();
			
			if (currentClouds == null) {
				currentClouds = (CloudsScreen)context.getActivity().getIntent().getSerializableExtra(JelloActivity.KEY_CURRENT_CLOUDS);
				nextClouds = (CloudsScreen)context.getActivity().getIntent().getSerializableExtra(JelloActivity.KEY_NEXT_CLOUDS);
			}
			
			isInitialized = true;
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public int getBestScore() {
		return bestScore;
	}
	
	public boolean isGoPressed() {
		return isGoPressed;
	}
	
	@Override
	public void handleTouch(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		int width = context.getGraphics().getWidth();
		int height = context.getGraphics().getHeight();
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (!isGameStarted && !isGameEnd) {
//			if (isOnResume && !isGameEnd) {
				isGameStarted = true;
				
				if (slime.getState() == State.STANDING)
					slime.setState(State.SLIDING);
			}
			else if (isGameEnd) {
				if (x >= width / 2 - JelloResources.GO_BUTTON.getWidth() / 2
						&& x <= width / 2 + JelloResources.GO_BUTTON.getWidth() / 2
						&& y >= height / 2 - JelloResources.END_GAME_WINDOW.getHeight() / 2 + 9 * JelloResources.END_GAME_WINDOW.getHeight() / 16
						&& y <= height / 2 + JelloResources.GO_BUTTON.getHeight() - JelloResources.END_GAME_WINDOW.getHeight() / 2 + 9 * JelloResources.END_GAME_WINDOW.getHeight() / 16) {
					playSound(R.raw.button);
					isGoPressed = true;
				}
			}
			else
				slime.jump();
		}
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (isGoPressed) {
				isGoPressed = false;
				isInitialized = false;
				initialize();
//				gameLoop.stopGameLoop();
//				Intent intent = new Intent(context.getActivity(), JelloGameActivity.class);
//				intent.putExtra(JelloActivity.KEY_CURRENT_CLOUDS, (Serializable)currentClouds);
//				intent.putExtra(JelloActivity.KEY_NEXT_CLOUDS, (Serializable)nextClouds);
//				context.getActivity().startActivityForResult(intent, JelloMenuActivity.GAME_ID);
//				context.getActivity().finish();
			}
		}
	}
	
	public JelloGameManager(JelloContext context) {
		super(context);
	}
	
	public Slime getSlime() {
		return slime;
	}
	
	public ForegroundScreen getCurrentForegroundScreen() {
		return currentForegroundScreen;
	}
	
	public ForegroundScreen getNextForegroundScreen() {
		return nextForegroundScreen;
	}
	
	public BackgroundScreen getCurrentBackgroundScreen() {
		return currentBackgroundScreen;
	}
	
	public BackgroundScreen getNextBackgroundScreen() {
		return nextBackgroundScreen;
	}
	
	public CloudsScreen getCurrentClouds() {
		return currentClouds;
	}
	
	public CloudsScreen getNextClouds() {
		return nextClouds;
	}
	
	public void playSound(final int soundId) {
		if (player != null) {
			player.release();
			player = null;
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				player = MediaPlayer.create(context.getActivity(), soundId);
				player.start();
				
				if (player != null)
					player.setOnCompletionListener(new OnCompletionListener() {
					    public void onCompletion(MediaPlayer mp) {
					    	if (mp != null)
					    		mp.release();
					    };
					});
			}
		}).start();
	}
	
	@Override
	public void onPause() {
		isGameStarted = false;
		
		if (player != null) {
			player.release();
			player = null;
		}
		context.getActivity().writeParameters();
		
//		Intent returnedIntent = new Intent();//getIntent();
//		returnedIntent.putExtra(JelloActivity.KEY_CURRENT_CLOUDS, currentClouds);
//		returnedIntent.putExtra(JelloActivity.KEY_NEXT_CLOUDS, nextClouds);
////		context.getActivity().setResult(Activity.RESULT_OK, returnedIntent);
////		if (context.getActivity().getParent() == null)
//		context.getActivity().setResult(Activity.RESULT_OK, returnedIntent);
	}
	
	@Override
	public void onResume() {
		if (slime != null && slime.getState() == State.SLIDING)
			slime.setState(State.STANDING);
	}
	
	public boolean isNewBestScore() {
		return isNewBestScore;
	}
	
	@Override
	public void progressGame() {
		// Progress cloud screens
		currentClouds.onGameProgress();
		nextClouds.onGameProgress();
		
		if (currentClouds.isOver()) {
			currentClouds = nextClouds;
			nextClouds = new CloudsScreen(context, JelloManager.CLOUDS_SPEED);
		}
		
		Intent returnedIntent = new Intent();//getIntent();
		returnedIntent.putExtra(JelloActivity.KEY_CURRENT_CLOUDS, (Serializable)currentClouds);
		returnedIntent.putExtra(JelloActivity.KEY_NEXT_CLOUDS, (Serializable)nextClouds);
		context.getActivity().setResult(Activity.RESULT_OK, returnedIntent);
					
		if (isGameStarted) {
			slime.onGameProgress();
			
			// Progress foreground screens
			currentForegroundScreen.onGameProgress();
			nextForegroundScreen.onGameProgress();
			
			if (currentForegroundScreen.isOver()) {
				currentForegroundScreen = nextForegroundScreen;
				nextForegroundScreen = new ForegroundScreen(context, JelloManager.FOREGROUND_SPEED);
			}
			
			// Progress background screens
			currentBackgroundScreen.onGameProgress();
			nextBackgroundScreen.onGameProgress();
			
			if (currentBackgroundScreen.isOver()) {
				currentBackgroundScreen = nextBackgroundScreen;
				nextBackgroundScreen = new BackgroundScreen(context, JelloManager.BACKGROUND_SPEED);
			}
			
			if (slime.isOutOfScreen()) {
				if (bestScore < score) {
					bestScore = score;
					isNewBestScore = true;
					context.getActivity().writeParameters();
				}
				else
					isNewBestScore = false;
				
				isGameEnd = true;
				JelloResources.JUMP_ANIMATION.resetAnimation();
				isGameStarted = false;
			}
		}
	}
	
	public void onPassed() {
		if (slime.getState() != State.DYING)
			score++;
	}
	
	public void onCollision() {
		slime.setOnHole(true);
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}
}
