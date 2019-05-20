package com.droplay.jello.game.objects;

import java.io.Serializable;

import com.droplay.jello.JelloContext;
import com.droplay.jello.JelloResources;
import com.droplay.jello.JelloScreen;
import com.droplay.jello.R;
import com.droplay.jello.game.JelloGameManager;

public class Hole extends Tile implements Serializable {
	private static final long serialVersionUID = 9146338712717708251L;
	
	protected boolean isPassed;
	private Slime slime;
	private int manSize;
	private JelloContext context;
	
	public Hole(JelloContext context, JelloScreen screen, int x) {
		super(screen, TileType.HOLE, x);
		this.context = context;
		this.slime = ((JelloGameManager)context.getManager()).getSlime();
		this.manSize = JelloResources.STAND_ANIMATION.getCurrentFrame().getWidth() - (int)context.getActivity().getResources().getDimension(R.dimen.slime_mergin);
	}
	
	@Override
	public void onGameProgress() {
		super.onGameProgress();
		
		if (!isPassed && x + JelloResources.HOLE.getWidth() <= slime.x + manSize * 48 / 100) {
			isPassed = true;
			((JelloGameManager)context.getManager()).onPassed();
		}
		
		if (slime.x + manSize / 2 >= x && slime.x + manSize * 80 / 100 <= x + JelloResources.HOLE.getWidth())
			((JelloGameManager)context.getManager()).onCollision();
	}
}
