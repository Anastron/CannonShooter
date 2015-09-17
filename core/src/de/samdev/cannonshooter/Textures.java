package de.samdev.cannonshooter;

import com.badlogic.gdx.graphics.Texture;

public final class Textures {
	public static Texture texbackground;
	
	public static Texture cannon_body;
	public static Texture cannon_barrel;
	public static Texture cannon_hearth;
	public static Texture cannon_bullet;
	
	public static void init() {
		texbackground = new Texture("level_background.png");
		
		cannon_body = new Texture("cannon_body.png");
		cannon_barrel = new Texture("cannon_barrel.png");
	}
}
