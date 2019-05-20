package com.droplay.jello;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.droplay.jello.game.JelloGameManager;
import com.droplay.jello.menu.JelloMenuManager;
import com.droplay.jello.menu.JelloMenuManager.ButtonState;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.BaseGameActivity;

public abstract class JelloActivity extends BaseGameActivity {
	protected JelloContext context;
	protected JelloGraphics graphicsView;
	protected JelloManager gameManager;
	private boolean isShowLeaderboard;
	private AdView adView;
	private boolean isInitialized;
	// Ads
	public static final String AD_UNIT_ID = "ca-app-pub-1600129559779321/6565669091";
	
	// Intents keys
	public static final String KEY_CURRENT_CLOUDS = "com.droplay.CURRENT_CLOUDS";
	public static final String KEY_NEXT_CLOUDS = "com.droplay.NEXT_CLOUDS";
	
	// Shared preferences
	public static final String GAME_PREFS = "GAME_PREFS";
	public static final String GAME_PREFS_BEST_SCORE = "GAME_PREFS_BEST_SCORE";
	
	// Activity results
	public static final int GAME_ID = 0;
	public static final int LEADERSBOARD_ID = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getGameHelper().setConnectOnStart(false);
		getGameHelper().setMaxAutoSignInAttempts(0);
		setContext();
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);

	    LinearLayout layout = new LinearLayout(this);
	    layout.setOrientation(LinearLayout.VERTICAL);
	    setContentView(layout);
	    layout.addView(adView);
	    layout.addView(graphicsView);
		AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("E6F3B1A44CC4F152A845F9F1DE53E41E")
        .build();

		// Start loading the ad in the background.
	    adView.loadAd(adRequest);
	}
	
	public void readParameters() {
		((JelloGameManager)gameManager).setBestScore(getSharedPreferences(GAME_PREFS, MODE_PRIVATE).getInt(GAME_PREFS_BEST_SCORE, 0));
	}
	
	public void writeParameters() {
		getSharedPreferences(GAME_PREFS, MODE_PRIVATE).edit().putInt(GAME_PREFS_BEST_SCORE, ((JelloGameManager)gameManager).getBestScore()).commit();
	}
	
	public void showLeaderboard() {
		isShowLeaderboard = true;
		if(getApiClient().isConnected()) {
			sendAndShowLeaderboard();
            isShowLeaderboard = false;
        }
		else
			beginUserInitiatedSignIn();
	}
	
	public void sendAndShowLeaderboard() {
		int bestScore = getSharedPreferences(GAME_PREFS, Context.MODE_PRIVATE).getInt(GAME_PREFS_BEST_SCORE, 0); 
		Games.Leaderboards.submitScore(getApiClient(),
                getString(R.string.leaderboard_id),
                bestScore);
		loadScoreOfLeaderBoard(bestScore);
		
        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                getApiClient(), getString(R.string.leaderboard_id)),
                LEADERSBOARD_ID);
	}
	
	private void loadScoreOfLeaderBoard(final int currentBestScore) {
	    Games.Leaderboards.loadCurrentPlayerLeaderboardScore(getApiClient(), getString(R.string.leaderboard_id), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
	        @Override
	        public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
	            if (isScoreResultValid(scoreResult)) {
	                // here you can get the score like this
	            	int currentScore = (int)scoreResult.getScore().getRawScore();
	    
	            	if (currentScore > currentBestScore)
	            		getSharedPreferences(GAME_PREFS, Context.MODE_PRIVATE).edit().putInt(GAME_PREFS_BEST_SCORE, currentScore).commit();
	            }
	        }
	    });
	}

	private boolean isScoreResultValid(final Leaderboards.LoadPlayerScoreResult scoreResult) {
	    return scoreResult != null && GamesStatusCodes.STATUS_OK == scoreResult.getStatus().getStatusCode() && scoreResult.getScore() != null;
	}

	@Override
	public void onSignInFailed() {
		if (gameManager instanceof JelloMenuManager)
			((JelloMenuManager)gameManager).setButtonState(ButtonState.NOTHING_PRESSED);
//		Toast.makeText(this, "Couldn't connect to google play!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSignInSucceeded() {
		if (isShowLeaderboard) {
			sendAndShowLeaderboard();
	        isShowLeaderboard = false;
		}
	}
	
	@Override
	protected void onPause() {
		if (adView != null)
	      adView.pause();

		super.onPause();
		
		gameManager.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (adView != null)
	      adView.resume();

		gameManager.onResume();
	}
	
	@Override
	public void onDestroy() {
    	// Destroy the AdView.
		if (adView != null)
			adView.destroy();
		
		super.onDestroy();
	}
	
	public void setContext() {
		context = new JelloContext();
		context.setActivity(this);
		setManagerAndGraphics();
		context.setGraphics(graphicsView);
		context.setManager(gameManager);
		context.setImages(new JelloResources(this));
		context.getImages().resetAllAnimations();
		isInitialized = true;
	}
	
	public abstract void setManagerAndGraphics();
	
	public boolean isInitialized() {
		return isInitialized;
	}
}
