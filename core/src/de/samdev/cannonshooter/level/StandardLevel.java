package de.samdev.cannonshooter.level;

import com.badlogic.gdx.math.Vector2;

import de.samdev.absgdx.framework.AgdxGame;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.absgdx.framework.map.TileMap;
import de.samdev.absgdx.framework.map.mapscaleresolver.ShowCompleteMapScaleResolver;
import de.samdev.cannonshooter.Textures;
import de.samdev.cannonshooter.entities.Cannon;
import de.samdev.cannonshooter.framework.TileAlignedBackground;

public class StandardLevel extends GameLayer {

	public StandardLevel(AgdxGame owner) {
		super(owner, TileMap.createEmptyMap(32, 20));
		
		addBackground(new TileAlignedBackground(Textures.texbackground, 1));

		setMapScaleResolver(new ShowCompleteMapScaleResolver());

		addEntity(new Cannon(10, 10));
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
