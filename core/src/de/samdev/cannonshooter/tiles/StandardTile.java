package de.samdev.cannonshooter.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.samdev.absgdx.framework.entities.colliosiondetection.CollisionGeometryOwner;
import de.samdev.absgdx.framework.map.Tile;

public class StandardTile extends Tile {

	public StandardTile() {
		super((TextureRegion)null);
	}

	@Override
	public boolean canMoveCollideWith(CollisionGeometryOwner other) {
		return false;
	}
	
	@Override
	public boolean canCollideWith(CollisionGeometryOwner other) {
		return false;
	}

	@Override
	public void update(float delta) { /* */ }

}
