package de.samdev.cannonshooter;

import com.badlogic.gdx.graphics.Texture;

public final class Textures {
	public static Texture texbackground;
	public static Texture cannon;
	
	public static void init() {
		texbackground = new Texture("level_background.png");
		cannon = new Texture("cannon.png");
	}
}
