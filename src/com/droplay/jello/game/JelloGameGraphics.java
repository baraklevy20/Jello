package com.droplay.jello.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.SurfaceHolder;

import com.droplay.jello.JelloContext;
import com.droplay.jello.JelloGraphics;
import com.droplay.jello.JelloResources;
import com.droplay.jello.JelloScreen;
import com.droplay.jello.R;
import com.droplay.jello.game.objects.Slime;
import com.droplay.jello.game.objects.Tile;
import com.droplay.jello.game.objects.TileType;

public class JelloGameGraphics extends JelloGraphics {
		private int width, height, groundY;
		private Slime slime;
		
		public JelloGameGraphics(JelloContext context) {
			super(context);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			this.width = w;
			this.height = h;
			
			groundY = h - JelloResources.GROUND.getHeight();
		}
		
		@Override
		public void doDraw(Canvas canvas) {
			slime = ((JelloGameManager)context.getManager()).getSlime();
			
			// Draw background
			canvas.drawRect(0, 0, width, height, JelloResources.BACKGROUND_PAINT);
			
			// Draw bottom background
			canvas.drawRect(0, groundY - height * 3 / 100, width, height, JelloResources.BOTTOM_BACKGROUND_PAINT);
			
			// Draw screens
			drawScreen(canvas, ((JelloGameManager)context.getManager()).getCurrentClouds());
			drawScreen(canvas, ((JelloGameManager)context.getManager()).getCurrentClouds());
			drawScreen(canvas, ((JelloGameManager)context.getManager()).getNextClouds());
			drawScreen(canvas, ((JelloGameManager)context.getManager()).getCurrentBackgroundScreen());
			drawScreen(canvas, ((JelloGameManager)context.getManager()).getNextBackgroundScreen());
			drawScreen(canvas, ((JelloGameManager)context.getManager()).getCurrentForegroundScreen());
			drawScreen(canvas, ((JelloGameManager)context.getManager()).getNextForegroundScreen());
			
			if (!((JelloGameManager)context.getManager()).isGameEnded()) {
				// Draw slime
				switch (slime.getState()) {
				case STANDING:
					canvas.drawBitmap(JelloResources.STAND_ANIMATION.getCurrentFrame(), slime.getX(), slime.getY(), null);
					break;
				case SLIDING:
					canvas.drawBitmap(JelloResources.SLIDE_ANIMATION.getCurrentFrame(), slime.getX(), slime.getY(), null);
					break;
				case IN_AIR:
					canvas.drawBitmap(JelloResources.BOOST, slime.getX(), slime.getY(), null);
					break;
				case START_JUMPING:
				case FALLING:
					canvas.drawBitmap(JelloResources.JUMP_ANIMATION.getCurrentFrame(), slime.getX(), slime.getY(), null);
					break;
				case IMPACT:
					canvas.drawBitmap(JelloResources.IMPACT_ANIMATION.getCurrentFrame(), slime.getX(), slime.getY(), null);
					break;
				case DYING:
					canvas.drawBitmap(JelloResources.DYING_ANIMATION.getCurrentFrame(), slime.getX(), slime.getY(), null);
					break;
				}
				
				// Draw score
				JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFFE4ECFF);
				JelloResources.VOLTER_GOLDFISH_PAINT.setTextSize(getResources().getDimension(R.dimen.score));
				Rect bounds = new Rect();
				String currentString = String.valueOf(((JelloGameManager)context.getManager()).getScore());
				JelloResources.VOLTER_GOLDFISH_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds);
				canvas.drawText(currentString, width / 2 - bounds.right / 2, height / 2 + bounds.top / 2, JelloResources.VOLTER_GOLDFISH_PAINT);
			
				if (!((JelloGameManager)context.getManager()).isGameStarted()) {
					bounds = new Rect();
					currentString = "Tap while jumping";
					JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFF829AD8);
					JelloResources.VOLTER_GOLDFISH_PAINT.setTextSize(getResources().getDimension(R.dimen.help_text));
					JelloResources.VOLTER_GOLDFISH_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds);
					canvas.drawText(currentString, slime.getX() + JelloResources.BOOST.getWidth() / 2 - bounds.right / 2 - getResources().getDimension(R.dimen.one_dp), slime.getY() - bounds.top - JelloResources.HOLE.getHeight() / 4 + getResources().getDimension(R.dimen.one_dp), JelloResources.VOLTER_GOLDFISH_PAINT);
					Rect bounds2 = new Rect();
					currentString = "for a boost";
					JelloResources.VOLTER_GOLDFISH_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds2);
					canvas.drawText(currentString, slime.getX() + JelloResources.BOOST.getWidth() / 2 - bounds.right / 2 - getResources().getDimension(R.dimen.one_dp), slime.getY() - bounds.top - 2 * bounds2.top - JelloResources.HOLE.getHeight() / 4 + getResources().getDimension(R.dimen.one_dp), JelloResources.VOLTER_GOLDFISH_PAINT);
					
					JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFFE4ECFF);
					canvas.drawText("Tap while jumping", slime.getX() + JelloResources.BOOST.getWidth() / 2 - bounds.right / 2, slime.getY() - bounds.top - JelloResources.HOLE.getHeight() / 4, JelloResources.VOLTER_GOLDFISH_PAINT);
					canvas.drawText("for a boost", slime.getX() + JelloResources.BOOST.getWidth() / 2 - bounds.right / 2, slime.getY() - bounds.top - 2 * bounds2.top - JelloResources.HOLE.getHeight() / 4, JelloResources.VOLTER_GOLDFISH_PAINT);
				
					canvas.drawBitmap(JelloResources.TIP, slime.getX() + JelloResources.BOOST.getWidth() / 2 - bounds.right / 2 - JelloResources.TIP.getWidth() - getWidth() / 40, slime.getY() - JelloResources.HOLE.getHeight() / 4, null);
				}
			}
			else {
				String currentString;
				canvas.drawBitmap(JelloResources.END_GAME_WINDOW, width / 2 - JelloResources.END_GAME_WINDOW.getWidth() / 2, height / 2 - JelloResources.END_GAME_WINDOW.getHeight() / 2, null);
				
				// Draw "your score"
				JelloResources.VOLTER_GOLDFISH_PAINT.setTextSize(getResources().getDimension(R.dimen.end_game_your_score));
				Rect bounds = new Rect();
				currentString = "Your score";
				JelloResources.VOLTER_GOLDFISH_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds);
				JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFFEBF7FF);
				canvas.drawText(currentString, width / 2 - bounds.right / 2, height / 2 + getResources().getDimension(R.dimen.end_game_shadow_mergin) - JelloResources.END_GAME_WINDOW.getHeight() / 4, JelloResources.VOLTER_GOLDFISH_PAINT);
				JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFF009DFF);
				canvas.drawText(currentString, width / 2 - bounds.right / 2, height / 2 - JelloResources.END_GAME_WINDOW.getHeight() / 4, JelloResources.VOLTER_GOLDFISH_PAINT);
				
				// Draw score
				currentString = String.valueOf(((JelloGameManager)context.getManager()).getScore());
				JelloResources.VOLTER_GOLDFISH_PAINT.setTextSize(getResources().getDimension(R.dimen.end_game_score));
				JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFF009DFF);
				JelloResources.VOLTER_GOLDFISH_PAINT.setTypeface(Typeface.create(JelloResources.VOLTER_GOLDFISH_PAINT.getTypeface(), Typeface.BOLD));
				bounds = new Rect();
				JelloResources.VOLTER_GOLDFISH_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds);
				canvas.drawText(currentString, width / 2 - bounds.right / 2, height / 2 - JelloResources.END_GAME_WINDOW.getHeight() / 2 + 7 * JelloResources.END_GAME_WINDOW.getHeight() / 16, JelloResources.VOLTER_GOLDFISH_PAINT);
				JelloResources.VOLTER_GOLDFISH_PAINT.setTypeface(Typeface.create(JelloResources.VOLTER_GOLDFISH_PAINT.getTypeface(), Typeface.NORMAL));
				
				// Draw go
				boolean isGoPressed = ((JelloGameManager)context.getManager()).isGoPressed();
				if (isGoPressed)
					canvas.drawBitmap(JelloResources.GO_BUTTON, width / 2 - JelloResources.GO_BUTTON.getWidth() / 2, height / 2 - JelloResources.END_GAME_WINDOW.getHeight() / 2 + 9 * JelloResources.END_GAME_WINDOW.getHeight() / 16 + JelloResources.GO_BUTTON.getHeight() / 4, null);
				else
					canvas.drawBitmap(JelloResources.GO_BUTTON, width / 2 - JelloResources.GO_BUTTON.getWidth() / 2, height / 2 - JelloResources.END_GAME_WINDOW.getHeight() / 2 + 9 * JelloResources.END_GAME_WINDOW.getHeight() / 16, null);
				
				JelloResources.VOLTER_GOLDFISH_PAINT.setTextSize(getResources().getDimension(R.dimen.end_game_go));
				JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFF83C349);
				bounds = new Rect();
				currentString = "Go!";
				JelloResources.VOLTER_GOLDFISH_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds);
				if (isGoPressed)
					canvas.drawText(currentString, width / 2 - bounds.right / 2, height / 2 - JelloResources.END_GAME_WINDOW.getHeight() / 2 + 9 * JelloResources.END_GAME_WINDOW.getHeight() / 16 + JelloResources.GO_BUTTON.getHeight() / 2 - bounds.top / 2 + JelloResources.GO_BUTTON.getHeight() / 4, JelloResources.VOLTER_GOLDFISH_PAINT);
				else
					canvas.drawText(currentString, width / 2 - bounds.right / 2, height / 2 - JelloResources.END_GAME_WINDOW.getHeight() / 2 + 9 * JelloResources.END_GAME_WINDOW.getHeight() / 16 + JelloResources.GO_BUTTON.getHeight() / 2 - bounds.top / 2, JelloResources.VOLTER_GOLDFISH_PAINT);
				// Draw "new best!" or "best score: %d"
				if (((JelloGameManager)context.getManager()).isNewBestScore())
					currentString = "New best!";
				else
					currentString = "Best score: " + ((JelloGameManager)context.getManager()).getBestScore();
				
				JelloResources.VOLTER_GOLDFISH_PAINT.setTextSize(getResources().getDimension(R.dimen.end_game_new_best));
				
				bounds = new Rect();
				JelloResources.VOLTER_GOLDFISH_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds);
				JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFFEBF7FF);
				canvas.drawText(currentString, width / 2 - bounds.right / 2, height / 2 - JelloResources.END_GAME_WINDOW.getHeight() / 2 + 119 * JelloResources.END_GAME_WINDOW.getHeight() / 128 + getResources().getDimension(R.dimen.end_game_shadow_mergin), JelloResources.VOLTER_GOLDFISH_PAINT);
				JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFF556670);
				canvas.drawText(currentString, width / 2 - bounds.right / 2, height / 2 - JelloResources.END_GAME_WINDOW.getHeight() / 2 + 119 * JelloResources.END_GAME_WINDOW.getHeight() / 128, JelloResources.VOLTER_GOLDFISH_PAINT);
			}
		}
		
		public int getGroundY() {
			return groundY;
		}
		
		public void drawScreen(Canvas canvas, JelloScreen screen) {
			Tile[] tiles = screen.getTiles();
			
			int tilesLength = tiles.length;
			
			for (int i = 0; i < tilesLength; i++) {
				switch (tiles[i].getType()) {
				case GROUND:
					canvas.drawBitmap(JelloResources.GROUND, tiles[i].getX(), groundY, null);
					break;
				case HOLE:
					canvas.drawBitmap(JelloResources.HOLE, tiles[i].getX(), groundY, null);
					break;
				case PLANT:
					canvas.drawBitmap(JelloResources.PLANTS[tiles[i].getSubType()], tiles[i].getX(),
							groundY - JelloResources.PLANTS[tiles[i].getSubType()].getHeight() +
							(int)getResources().getDimension(R.dimen.plant_mergin), null);
					break;
				case TREE:
					canvas.drawBitmap(JelloResources.TREES[tiles[i].getSubType()], tiles[i].getX(), groundY - JelloResources.TREES[tiles[i].getSubType()].getHeight() + getResources().getDimension(R.dimen.tree_mergin), null);
					break;
				case CLOUD:
					canvas.drawBitmap(JelloResources.CLOUDS[tiles[i].getSubType()], tiles[i].getX(), tiles[i].getY(), null);
					break;
				case EMPTY:
					break;
				default:
					break;
				}
			}
			
			if (screen instanceof ForegroundScreen) {
				tiles = ((ForegroundScreen)screen).getPlants();
				tilesLength = tiles.length;
				
				for (int i = 0; i < tilesLength; i++) {
					if (tiles[i].getType() == TileType.PLANT)
						canvas.drawBitmap(JelloResources.PLANTS[tiles[i].getSubType()], tiles[i].getX(),
								groundY - JelloResources.PLANTS[tiles[i].getSubType()].getHeight() +
								(int)getResources().getDimension(R.dimen.plant_mergin), null);
				}
			}
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			super.surfaceCreated(holder);
		}
	}