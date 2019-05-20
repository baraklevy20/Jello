package com.droplay.jello.game.objects;

import android.util.Log;

import com.droplay.jello.JelloContext;
import com.droplay.jello.JelloResources;
import com.droplay.jello.R;
import com.droplay.jello.game.JelloGameGraphics;
import com.droplay.jello.game.JelloGameManager;


public class Slime extends MovableGameObject {
	private static final long serialVersionUID = -6779091256104491833L;
	private float velocity;
	private boolean isDyingable = true;
	public static final float GRAVITY = 0.5f;
	public static final float SECOND_GRAVITY = 0.1f;
	public static final int JUMP = 6;
	private JelloContext context;
	private int manSize, holeWidth;
	private State state;
	private long startingJumpTime;
	private int currentJump;
	public static final int TIME_TO_FALL = 400;
	public static final float SECOND_JUMP = 4;
	private boolean isOnHole = false;
	private boolean inImpact = false;
	
	public Slime(JelloContext context) {
		this.context = context;
		this.manSize = JelloResources.STAND_ANIMATION.getCurrentFrame().getWidth() - (int)context.getActivity().getResources().getDimension(R.dimen.slime_mergin);
		this.holeWidth = JelloResources.HOLE.getWidth();
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	public void jump() {
		if (state == State.SLIDING) {
			currentJump = 0;
			state = State.START_JUMPING;
		}
		
		if (state == State.IN_AIR && !inImpact) {
			if (System.currentTimeMillis() - startingJumpTime <= 2000) {
				if (currentJump < 6)
					currentJump++;
				
				switch (currentJump) {
				case 1: ((JelloGameManager)context.getManager()).playSound(R.raw.b01); break;
				case 2: ((JelloGameManager)context.getManager()).playSound(R.raw.b02); break;
				case 3: ((JelloGameManager)context.getManager()).playSound(R.raw.b03); break;
				case 4: ((JelloGameManager)context.getManager()).playSound(R.raw.b04); break;
				case 5: ((JelloGameManager)context.getManager()).playSound(R.raw.b05); break;
				case 6: ((JelloGameManager)context.getManager()).playSound(R.raw.b06); break;
				}
				
//				velocity = -secondJump;
				y -= SECOND_JUMP * 8.85 * holeWidth / 256;
			}
		}
	}
	
	@Override
	public void onGameProgress() {
		if (state != State.DYING && isOnHole && isDyingable && y + manSize >= ((JelloGameGraphics)context.getGraphics()).getGroundY()) {
			((JelloGameManager)context.getManager()).playSound(R.raw.fall);
			state = State.DYING;
		}
			
		if (state == State.START_JUMPING) {
			if (JelloResources.JUMP_ANIMATION.getCurrentFrameIndex() == 5) {
				((JelloGameManager)context.getManager()).playSound(R.raw.jump);
				state = State.IN_AIR;
				y -= JUMP * 8.85 * holeWidth / 256;
				startingJumpTime = System.currentTimeMillis();
			}
		}
		if (state == State.IN_AIR && System.currentTimeMillis() - startingJumpTime > TIME_TO_FALL && y < ((JelloGameGraphics)context.getGraphics()).getGroundY() - manSize) {
			state = State.FALLING;
		}
		
		// Handles gravity
		if (state == State.FALLING || state == State.DYING) {
			velocity += GRAVITY;
			y += velocity * 8.85 * holeWidth / 256;
		}
		
		if (state == State.IN_AIR) {
			velocity += SECOND_GRAVITY;
			y += velocity * 8.85 * holeWidth / 256;
		}

		// Handles impact
		if ((state == State.IN_AIR || state == State.FALLING) && y > ((JelloGameGraphics)context.getGraphics()).getGroundY() - manSize) {
			inImpact = true;
			y = ((JelloGameGraphics)context.getGraphics()).getGroundY() - manSize; // Just in case the slime moves too much due to high gravity
			if (isOnHole && isDyingable) {
				((JelloGameManager)context.getManager()).playSound(R.raw.fall);
				state = State.DYING;
			}
			else {
				velocity = 0;
				((JelloGameManager)context.getManager()).playSound(R.raw.land);
				state = State.IMPACT;
				JelloResources.JUMP_ANIMATION.resetAnimation();
			}
			inImpact = false;
		}
		
		// Handles sliding again
		if (state == State.IMPACT && JelloResources.IMPACT_ANIMATION.getCurrentFrameIndex() == JelloResources.IMPACT_ANIMATION.getNumberOfFrames() - 1) {
			state = State.SLIDING;
			JelloResources.IMPACT_ANIMATION.resetAnimation();
		}
		
		this.isOnHole = false;
		Log.v("slime state", state.toString());
	}
	
	public boolean isOutOfScreen() {
		return y >= context.getGraphics().getHeight();
	}
	
	public boolean isOnGround() {
		return y + manSize == ((JelloGameGraphics)context.getGraphics()).getGroundY();
	}

	public void setOnHole(boolean isOnHole) {
		this.isOnHole = isOnHole;
	}
}
