package com.droplay.jello;

public class JelloContext {
	private JelloGraphics graphics;
	private JelloManager manager;
	private JelloActivity activity;
	private JelloResources images;
	
	public JelloResources getImages() {
		return images;
	}
	
	public void setImages(JelloResources images) {
		this.images = images;
	}
	
	public void setGraphics(JelloGraphics graphics) {
		this.graphics = graphics;
	}

	public void setManager(JelloManager manager) {
		this.manager = manager;
	}

	public JelloGraphics getGraphics() {
		return graphics;
	}
	
	public JelloManager getManager() {
		return manager;
	}

	public JelloActivity getActivity() {
		return activity;
	}

	public void setActivity(JelloActivity activity) {
		this.activity = activity;
	}
}
