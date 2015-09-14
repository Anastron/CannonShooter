package de.samdev.cannonshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import de.samdev.absgdx.framework.AgdxGame;
import de.samdev.cannonshooter.level.StandardLevel;

public class CannonGame extends AgdxGame  {

	@Override
	public void onCreate() {
		Textures.init();
		
		setLayer(new StandardLevel(this));
		
		setDebugFont(new BitmapFont(Gdx.files.internal("consolefont.fnt")));
		
		settings.debugVisualMenu.set(false);
		settings.debugMenuLayerTextInfos.set(false);
		settings.debugEnabled.set(true);
	}

	@Override
	public void onUpdate(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.F1)) settings.debugEnabled.doSwitch();
		if (Gdx.input.isKeyJustPressed(Keys.F2)) settings.debugVisualEntities.doSwitch();
		if (Gdx.input.isKeyJustPressed(Keys.F3)) settings.debugVisualMap.doSwitch();
		if (Gdx.input.isKeyJustPressed(Keys.F4)) settings.debugVisualMenu.doSwitch();
		if (Gdx.input.isKeyJustPressed(Keys.F5)) settings.debugTextInfos.doSwitch();
		if (Gdx.input.isKeyJustPressed(Keys.F6)) settings.debugEntitiesPhysicVectors.doSwitch();
	}
}
