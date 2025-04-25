package com.pnot0.apeirongramma.screens;

import java.util.ArrayList;
import java.util.List;

public class MagicCircleCurrentPresets {
	public String textureLocation = "textures/gui/3sided_circle.png";
	public int guiSize = 512;
	public int edges = 3;
	
	public static final MagicCircleCurrentPresets INSTANCE = new MagicCircleCurrentPresets();
	
	private final List<IPresetObserver> observers = new ArrayList<>();
	
	public void setPreset(String textureLocation, int guiSize, int edges) {
		this.textureLocation = textureLocation;
		this.guiSize = guiSize;
		this.edges = edges;
		notifyObservers();
	}
	
	public void addObserver(IPresetObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(IPresetObserver observer) {
		observers.remove(observer);
	}
	
	private void notifyObservers() {
		for(IPresetObserver observer : observers) {
			observer.update(textureLocation, guiSize, edges);
		}
	}
}

