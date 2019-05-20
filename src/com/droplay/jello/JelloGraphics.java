package com.droplay.jello;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class JelloGraphics extends SurfaceView implements SurfaceHolder.Callback {
	protected JelloContext context;
	
	public JelloGraphics(JelloContext context) {
		super(context.getActivity());
		this.context = context;
		getHolder().addCallback(this);
		setFocusable(true);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		context.getManager().initialize();
		context.getManager().getGameLoop().startGameLoop(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		context.getManager().getGameLoop().stopGameLoop();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		context.getManager().handleTouch(event);
		return true;
	}
	
	public abstract void doDraw(Canvas canvas);
}
