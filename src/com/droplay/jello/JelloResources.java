package com.droplay.jello;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;

public class JelloResources {
	// Bitmaps
	public static Bitmap HOLE;
	public static Bitmap GROUND;
	public static Bitmap[] TREES;
	public static Bitmap[] PLANTS;
	public static Bitmap[] CLOUDS;
	public static Bitmap END_GAME_WINDOW;
	public static Bitmap GO_BUTTON, GO_BUTTON_PRESSED;
	public static Bitmap LOGO;
	public static Bitmap BOOST;
	public static Bitmap TIP;
	
	// Animations
	public static Animation STAND_ANIMATION;
	public static Animation SLIDE_ANIMATION;
	public static Animation JUMP_ANIMATION;
	public static Animation IMPACT_ANIMATION;
	public static Animation DYING_ANIMATION;
	
	// Paints
	public static Paint VOLTER_GOLDFISH_PAINT;
	public static Paint BACKGROUND_PAINT;
	public static Paint BOTTOM_BACKGROUND_PAINT;
	
	public void resetAllAnimations() {
		STAND_ANIMATION.resetAnimation();
		SLIDE_ANIMATION.resetAnimation();
		JUMP_ANIMATION.resetAnimation();
		IMPACT_ANIMATION.resetAnimation();
		DYING_ANIMATION.resetAnimation();
	}
	
	public JelloResources(Context context) {
		Resources resources = context.getResources();
		
		if (TIP == null)
			TIP = BitmapFactory.decodeResource(resources, R.drawable.tipico);
		
		if (BOOST == null)
			BOOST = BitmapFactory.decodeResource(resources, R.drawable.boost);
		
		if (HOLE == null)
			HOLE = BitmapFactory.decodeResource(resources, R.drawable.hole);
		
		if (GROUND == null)
			GROUND = BitmapFactory.decodeResource(resources, R.drawable.ground);
		
		if (TREES == null) {
			TREES = new Bitmap[3];
			TREES[0] = BitmapFactory.decodeResource(resources, R.drawable.tree_1);
			TREES[1] = BitmapFactory.decodeResource(resources, R.drawable.tree_2);
			TREES[2] = BitmapFactory.decodeResource(resources, R.drawable.tree_3);
		}
		
		if (PLANTS == null) {
			PLANTS = new Bitmap[4];
			PLANTS[0] = BitmapFactory.decodeResource(resources, R.drawable.plant_1);
			PLANTS[1] = BitmapFactory.decodeResource(resources, R.drawable.plant_2);
			PLANTS[2] = BitmapFactory.decodeResource(resources, R.drawable.plant_3);
			PLANTS[3] = BitmapFactory.decodeResource(resources, R.drawable.plant_4);
		}
		
		if (CLOUDS == null) {
			CLOUDS = new Bitmap[6];
			CLOUDS[0] = BitmapFactory.decodeResource(resources, R.drawable.cloud_1);
			CLOUDS[1] = BitmapFactory.decodeResource(resources, R.drawable.cloud_2);
			CLOUDS[2] = BitmapFactory.decodeResource(resources, R.drawable.cloud_3);
			CLOUDS[3] = BitmapFactory.decodeResource(resources, R.drawable.cloud_4);
			CLOUDS[4] = BitmapFactory.decodeResource(resources, R.drawable.cloud_5);
			CLOUDS[5] = BitmapFactory.decodeResource(resources, R.drawable.cloud_6);
		}
		
		if (END_GAME_WINDOW == null)
			END_GAME_WINDOW = BitmapFactory.decodeResource(resources, R.drawable.end_game_window);
		
		if (LOGO == null)
			LOGO = BitmapFactory.decodeResource(resources, R.drawable.logo);
		
		if (GO_BUTTON == null)
			GO_BUTTON = BitmapFactory.decodeResource(resources, R.drawable.go_button);
		
		if (GO_BUTTON_PRESSED == null)
			GO_BUTTON_PRESSED = BitmapFactory.decodeResource(resources, R.drawable.go_button_pressed);
		
		if (STAND_ANIMATION == null)
			STAND_ANIMATION = new Animation(0.5f, true,
					BitmapFactory.decodeResource(resources, R.drawable.breath_1),
					BitmapFactory.decodeResource(resources, R.drawable.breath_2),
					BitmapFactory.decodeResource(resources, R.drawable.breath_3),
					BitmapFactory.decodeResource(resources, R.drawable.breath_4));
		
		if (SLIDE_ANIMATION == null)
			SLIDE_ANIMATION = new Animation(0.1f, true,
					BitmapFactory.decodeResource(resources, R.drawable.slide_1),
					BitmapFactory.decodeResource(resources, R.drawable.slide_1),
					BitmapFactory.decodeResource(resources, R.drawable.slide_3),
					BitmapFactory.decodeResource(resources, R.drawable.slide_4));
		
		if (JUMP_ANIMATION == null)
			JUMP_ANIMATION = new Animation(0.05f, false,
					BitmapFactory.decodeResource(resources, R.drawable.jump_1),
					BitmapFactory.decodeResource(resources, R.drawable.jump_2),
					BitmapFactory.decodeResource(resources, R.drawable.jump_3),
					BitmapFactory.decodeResource(resources, R.drawable.jump_4),
					BitmapFactory.decodeResource(resources, R.drawable.jump_5),
					BitmapFactory.decodeResource(resources, R.drawable.jump_6),
					BitmapFactory.decodeResource(resources, R.drawable.jump_7),
					BitmapFactory.decodeResource(resources, R.drawable.jump_8));
		
		if (IMPACT_ANIMATION == null)
			IMPACT_ANIMATION = new Animation(0.0825f, false,
					BitmapFactory.decodeResource(resources, R.drawable.fall_1),
					BitmapFactory.decodeResource(resources, R.drawable.fall_2),
					BitmapFactory.decodeResource(resources, R.drawable.fall_3),
					BitmapFactory.decodeResource(resources, R.drawable.fall_4),
					BitmapFactory.decodeResource(resources, R.drawable.fall_5),
					BitmapFactory.decodeResource(resources, R.drawable.fall_6),
					BitmapFactory.decodeResource(resources, R.drawable.fall_7),
					BitmapFactory.decodeResource(resources, R.drawable.fall_8),
					BitmapFactory.decodeResource(resources, R.drawable.fall_9));
		
		if (DYING_ANIMATION == null)
			DYING_ANIMATION = new Animation(0.1f, false,
					BitmapFactory.decodeResource(resources, R.drawable.die_1),
					BitmapFactory.decodeResource(resources, R.drawable.die_2),
					BitmapFactory.decodeResource(resources, R.drawable.die_3));
		
		if (VOLTER_GOLDFISH_PAINT == null) {
			VOLTER_GOLDFISH_PAINT = new Paint();
			Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/volter_goldfish.ttf");
			VOLTER_GOLDFISH_PAINT.setTypeface(tf);
		}
		
		if (BACKGROUND_PAINT == null) {
			BACKGROUND_PAINT = new Paint();
			BACKGROUND_PAINT.setStyle(Style.FILL);
			BACKGROUND_PAINT.setColor(0xFF92B0FE);
		}
		
		if (BOTTOM_BACKGROUND_PAINT == null) {
			BOTTOM_BACKGROUND_PAINT = new Paint();
			BOTTOM_BACKGROUND_PAINT.setStyle(Style.FILL);
			BOTTOM_BACKGROUND_PAINT.setColor(0xFFA9C1FF);
		}
	}
}