package de.samdev.cannonshooter.level;

import com.badlogic.gdx.math.Vector2;

import de.samdev.absgdx.framework.AgdxGame;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.absgdx.framework.map.TileMap;
import de.samdev.absgdx.framework.map.background.RepeatingBackground;
import de.samdev.absgdx.framework.map.mapscaleresolver.ShowCompleteMapScaleResolver;
import de.samdev.cannonshooter.Textures;
import de.samdev.cannonshooter.entities.Cannon;

public class StandardLevel extends GameLayer {

	public StandardLevel(AgdxGame owner) {
		super(owner, TileMap.createEmptyMap(32, 20));
		
		addBackground(new RepeatingBackground(Textures.texbackground, 1/32f));

		setMapScaleResolver(new ShowCompleteMapScaleResolver());

		addEntity(new Cannon(7, 13));

		addEntity(new Cannon(14, 5));

		addEntity(new Cannon(20, 13));
	}
	
	@Override
	public void onResize() {
		super.onResize();
		
		// Center Map
		setRawOffset(new Vector2(-(getVisibleMapBox().width - getMap().width)/2, -(getVisibleMapBox().height - getMap().height)/2));
	}

	@Override
	public void onUpdate(float arg0) {
		//
	}
}
