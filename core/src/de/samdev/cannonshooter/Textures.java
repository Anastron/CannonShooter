package de.samdev.cannonshooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.samdev.absgdx.framework.util.TextureHelper;

public final class Textures {
	public static Texture texbackground;
	
	public static Texture cannon_body;
	public static TextureRegion[] cannon_barrel;
	public static Texture cannon_hearth;
	public static Texture cannon_bullet;
	
	public static void init() {
		texbackground = new Texture("level_background.png");
		
		cannon_body = new Texture("cannon_body.png");
		cannon_barrel = TextureHelper.load1DArray("cannon_barrel.png", 512, 256, 16);
	}
}
