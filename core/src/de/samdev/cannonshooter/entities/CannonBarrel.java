package de.samdev.cannonshooter.entities;

import de.samdev.absgdx.framework.entities.Entity;
import de.samdev.absgdx.framework.entities.colliosiondetection.CollisionGeometryOwner;
import de.samdev.absgdx.framework.entities.colliosiondetection.geometries.CollisionGeometry;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.cannonshooter.Textures;
import de.samdev.cannonshooter.ZLayers;

public class CannonBarrel extends Entity {

	private float rotation = 0;
	
	public CannonBarrel(Cannon owner) {
		super(Textures.cannon_barrel, 4, 2);
		
		setPosition(owner.getPositionX() - 1, owner.getPositionY());
		
		setZLayer(ZLayers.LAYER_CANNON_BARREL);
	}

	@Override
	public void onActiveCollide(CollisionGeometryOwner passiveCollider, CollisionGeometry myGeo, CollisionGeometry otherGeo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPassiveCollide(CollisionGeometryOwner activeCollider, CollisionGeometry myGeo, CollisionGeometry otherGeo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActiveMovementCollide(CollisionGeometryOwner passiveCollider, CollisionGeometry myGeo, CollisionGeometry otherGeo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPassiveMovementCollide(CollisionGeometryOwner activeCollider, CollisionGeometry myGeo, CollisionGeometry otherGeo) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canCollideWith(CollisionGeometryOwner other) {
		return false;
	}

	@Override
	public boolean canMoveCollideWith(CollisionGeometryOwner other) {
		return false;
	}

	@Override
	public void beforeUpdate(float delta) {
		rotation += 1;
		rotation = (rotation + 1) % 360;
	}
	
	@Override
	public float getTextureRotation() {
		return rotation;
	}

	@Override
	public void onLayerAdd(GameLayer layer) {
		// TODO Auto-generated method stub

	}

}
