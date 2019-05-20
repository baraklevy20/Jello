package com.droplay.jello.menu;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import com.droplay.jello.JelloContext;
import com.droplay.jello.JelloGraphics;
import com.droplay.jello.JelloScreen;
import com.droplay.jello.R;
import com.droplay.jello.JelloResources;
import com.droplay.jello.game.ForegroundScreen;
import com.droplay.jello.game.objects.Tile;
import com.droplay.jello.game.objects.TileType;
import com.droplay.jello.menu.JelloMenuManager.ButtonState;

public class JelloMenuGraphics extends JelloGraphics {
	public static final int FLOATING_SPEED = 1;
	public static final int MAX_DISTANCE = 15;
	private float floatingDirection = 1;
	private float currentY;
	
	public JelloMenuGraphics(JelloContext context) {
		super(context);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		super.surfaceCreated(holder);
		currentY = getHeight() / 2 - JelloResources.LOGO.getHeight() - 2 * JelloResources.GO_BUTTON.getHeight();
	}
	
	@Override
	public void doDraw(Canvas canvas) {
		// Draw background
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), JelloResources.BACKGROUND_PAINT);
		
		// Draw bottom background
		canvas.drawRect(0, canvas.getHeight() - JelloResources.GROUND.getHeight() - canvas.getHeight() * 3 / 100, canvas.getWidth(), canvas.getHeight(), JelloResources.BOTTOM_BACKGROUND_PAINT);
		
		// Draw screens
		drawScreen(canvas, ((JelloMenuManager)context.getManager()).getCurrentClouds());
		drawScreen(canvas, ((JelloMenuManager)context.getManager()).getNextClouds());
		drawScreen(canvas, ((JelloMenuManager)context.getManager()).getCurrentTrees());
		drawScreen(canvas, ((JelloMenuManager)context.getManager()).getNextTrees());
		drawScreen(canvas, ((JelloMenuManager)context.getManager()).getCurrentScreen());
		drawScreen(canvas, ((JelloMenuManager)context.getManager()).getNextScreen());
			
		// Draw Logo
		canvas.drawBitmap(JelloResources.LOGO, canvas.getWidth() / 2 - JelloResources.LOGO.getWidth() / 2, currentY, null);
		currentY += floatingDirection * JelloResources.LOGO.getHeight() / FLOATING_SPEED / 188;
		
		if (Math.abs(getHeight() / 2 - JelloResources.LOGO.getHeight() - 2 * JelloResources.GO_BUTTON.getHeight() - currentY) > MAX_DISTANCE * JelloResources.LOGO.getHeight() / 188)
			floatingDirection = -floatingDirection;
		
		// Draw go
		ButtonState buttonState = ((JelloMenuManager)context.getManager()).getButtonState();
		
		if (buttonState == ButtonState.GO_PRESSED)
			canvas.drawBitmap(JelloResources.GO_BUTTON_PRESSED, canvas.getWidth() / 2 - JelloResources.GO_BUTTON.getWidth() / 2, canvas.getHeight() / 2 - JelloResources.GO_BUTTON.getHeight() + JelloResources.GO_BUTTON.getHeight() / 8, null);
		else
			canvas.drawBitmap(JelloResources.GO_BUTTON, canvas.getWidth() / 2 - JelloResources.GO_BUTTON.getWidth() / 2, canvas.getHeight() / 2 - JelloResources.GO_BUTTON.getHeight(), null);
		JelloResources.VOLTER_GOLDFISH_PAINT.setTextSize(getResources().getDimension(R.dimen.end_game_go));
		JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFF83C349);
		Rect bounds = new Rect();
		String currentString = "Go!";
		JelloResources.VOLTER_GOLDFISH_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds);
		
		if (buttonState == ButtonState.GO_PRESSED)
			canvas.drawText(currentString, canvas.getWidth() / 2 - bounds.right / 2, canvas.getHeight() / 2 - JelloResources.GO_BUTTON.getHeight() / 2 - bounds.top / 2 + JelloResources.GO_BUTTON.getHeight() / 8, JelloResources.VOLTER_GOLDFISH_PAINT);
		else
			canvas.drawText(currentString, canvas.getWidth() / 2 - bounds.right / 2, canvas.getHeight() / 2 - JelloResources.GO_BUTTON.getHeight() / 2 - bounds.top / 2, JelloResources.VOLTER_GOLDFISH_PAINT);
		
		// Draw scores
		if (buttonState == ButtonState.SCORES_PRESSED)
			canvas.drawBitmap(JelloResources.GO_BUTTON_PRESSED, canvas.getWidth() / 2 - JelloResources.GO_BUTTON.getWidth() / 2, canvas.getHeight() / 2 + JelloResources.GO_BUTTON.getHeight() / 2 + JelloResources.GO_BUTTON.getHeight() / 8, null);
		else
			canvas.drawBitmap(JelloResources.GO_BUTTON, canvas.getWidth() / 2 - JelloResources.GO_BUTTON.getWidth() / 2, canvas.getHeight() / 2 + JelloResources.GO_BUTTON.getHeight() / 2, null);
		
		JelloResources.VOLTER_GOLDFISH_PAINT.setTextSize(getResources().getDimension(R.dimen.end_game_go));
		JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFFE64B68);
		bounds = new Rect();
		currentString = "Scores";
		JelloResources.VOLTER_GOLDFISH_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds);
		
		if (buttonState == ButtonState.SCORES_PRESSED)
			canvas.drawText(currentString, canvas.getWidth() / 2 - bounds.right / 2, canvas.getHeight() / 2 + JelloResources.GO_BUTTON.getHeight() - bounds.top / 2 + JelloResources.GO_BUTTON.getHeight() / 8, JelloResources.VOLTER_GOLDFISH_PAINT);
		else
			canvas.drawText(currentString, canvas.getWidth() / 2 - bounds.right / 2, canvas.getHeight() / 2 + JelloResources.GO_BUTTON.getHeight() - bounds.top / 2, JelloResources.VOLTER_GOLDFISH_PAINT);

		// Draw rate us
		JelloResources.VOLTER_GOLDFISH_PAINT.setTextSize(getResources().getDimension(R.dimen.rate_us));
		if (buttonState == ButtonState.RATE_US_PRESSED) {
			JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFF4B5D67);
			canvas.drawText("Rate us", canvas.getWidth() / 14 - getResources().getDimension(R.dimen.one_dp), canvas.getWidth() / 10 + JelloResources.GO_BUTTON.getHeight() / 8, JelloResources.VOLTER_GOLDFISH_PAINT);
			canvas.drawText("Rate us", canvas.getWidth() / 14, canvas.getWidth() / 10 + getResources().getDimension(R.dimen.one_dp) + JelloResources.GO_BUTTON.getHeight() / 8, JelloResources.VOLTER_GOLDFISH_PAINT);
			JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFFF3F7FA);
			canvas.drawText("Rate us", canvas.getWidth() / 14, canvas.getWidth() / 10 + JelloResources.GO_BUTTON.getHeight() / 8, JelloResources.VOLTER_GOLDFISH_PAINT);
		}
		else {
			JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFF4B5D67);
			canvas.drawText("Rate us", canvas.getWidth() / 14 - getResources().getDimension(R.dimen.one_dp), canvas.getWidth() / 10, JelloResources.VOLTER_GOLDFISH_PAINT);
			canvas.drawText("Rate us", canvas.getWidth() / 14, canvas.getWidth() / 10 + getResources().getDimension(R.dimen.one_dp), JelloResources.VOLTER_GOLDFISH_PAINT);
			JelloResources.VOLTER_GOLDFISH_PAINT.setColor(0xFFF3F7FA);
			canvas.drawText("Rate us", canvas.getWidth() / 14, canvas.getWidth() / 10, JelloResources.VOLTER_GOLDFISH_PAINT);
		}
		
		// Draw Jello!
		canvas.drawBitmap(JelloResources.SLIDE_ANIMATION.getCurrentFrame(), context.getGraphics().getWidth() / 2 - (JelloResources.STAND_ANIMATION.getCurrentFrame().getWidth() - (int)context.getActivity().getResources().getDimension(R.dimen.slime_mergin)) / 2, canvas.getHeight() - JelloResources.GROUND.getHeight() - JelloResources.SLIDE_ANIMATION.getCurrentFrame().getHeight() + getResources().getDimension(R.dimen.slime_mergin), null);
	}
	
	public void drawScreen(Canvas canvas, JelloScreen screen) {
		Tile[] tiles = screen.getTiles();
		
		int tilesLength = tiles.length;
		int groundY = getHeight() - JelloResources.GROUND.getHeight();
		
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
}
