package de.samdev.cannonshooter.level;

import de.samdev.absgdx.framework.AgdxGame;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.absgdx.framework.map.TileMap;
import de.samdev.absgdx.framework.map.background.RepeatingBackground;
import de.samdev.cannonshooter.Textures;

public class StandardLevel extends GameLayer {

	public StandardLevel(AgdxGame owner) {
		super(owner, TileMap.createEmptyMap(32, 20));
		
		addBackground(new RepeatingBackground(Textures.texbackground, 32));
	}

	@Override
	public void onUpdate(float arg0) {
		// TODO Auto-generated method stub

	}

}
