package de.samdev.cannonshooter.level;

import java.util.Random;

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
		super(owner, TileMap.createEmptyMap(32, 32));
		
		addBackground(new TileAlignedBackground(Textures.texbackground, 1));

		setMapScaleResolver(new ShowCompleteMapScaleResolver());
		setRawOffset(new Vector2(-(getVisibleMapBox().width - getMap().width)/2, -(getVisibleMapBox().height - getMap().height)/2));
		
		
		Random R = new Random(80085);
		for (int i = 0; i < 24; i++) {
			addEntity(new Cannon(R.nextInt(15)*2+1, R.nextInt(15)*2+1));
		}
	}
	
	@Override
	public void onResize() {
		super.onResize();
		
		setRawOffset(new Vector2(-(getVisibleMapBox().width - getMap().width)/2, -(getVisibleMapBox().height - getMap().height)/2));
	}

	@Override
	public void onUpdate(float arg0) {
		//setRawOffset(new Vector2(-(getVisibleMapBox().width - getMap().width)/2, -(getVisibleMapBox().height - getMap().height)/2));
	}

}
