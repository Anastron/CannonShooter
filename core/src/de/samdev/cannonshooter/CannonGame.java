package de.samdev.cannonshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import de.samdev.absgdx.framework.AgdxGame;
import de.samdev.cannonshooter.level.StandardLevel;

public class CannonGame extends AgdxGame  {

	@Override
	public void onCreate() {
		Textures.init();
		
		pushLayer(new StandardLevel(this));
		
		setDebugFont(new BitmapFont(Gdx.files.internal("consolefont.fnt")));
	}

	@Override
	public void onUpdate(float arg0) {
		// TODO Auto-generated method stub
		
	}
}
