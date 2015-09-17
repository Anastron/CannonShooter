package de.samdev.cannonshooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import de.samdev.absgdx.framework.entities.Entity;
import de.samdev.absgdx.framework.entities.colliosiondetection.CollisionGeometryOwner;
import de.samdev.absgdx.framework.entities.colliosiondetection.geometries.CollisionGeometry;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.cannonshooter.Textures;
import de.samdev.cannonshooter.ZLayers;
import de.samdev.cannonshooter.util.MathUtils;

public class CannonBarrel extends Entity {
	private static final float CHARGE_DURATION = 0.0001f;
	private static final float ROTATION_SPEED = 0.18f;
	
	private boolean dragging = false;
	
	private float rotation = 0;
	private float targetRotation = 0;
	private float charge = 0;

	private Cannon cannon;
	
	public CannonBarrel(Cannon owner) {
		super(Textures.cannon_barrel[0], 4, 2);
		cannon = owner;
		
		setPosition(owner.getPositionX() - 1, owner.getPositionY());
		
		setZLayer(ZLayers.LAYER_CANNON_BARREL);
	}

	@Override
	public void beforeUpdate(float delta) {
		if (dragging) updateDragging();
		
		if (rotation != targetRotation) {
			float sign = MathUtils.moduloSignum(rotation, targetRotation, 360, 1);
			
			rotation += sign * ROTATION_SPEED * delta;
			if (sign != MathUtils.moduloSignum(rotation, targetRotation, 360, 1)) rotation = targetRotation;
			
			rotation = (rotation + 360) % 360;
		}
	}

	@Override
	public float getTextureRotation() {
		return rotation;
	}
	
	private void updateDragging() {
		if (! Gdx.input.isTouched()) {
			dragging = false;
			return;
		}
		
		Vector2 mouse = new Vector2(owner.getMouseOnMapPositionX() - cannon.getCenterX(), owner.getMouseOnMapPositionY() - cannon.getCenterY());
		
		targetRotation = mouse.angle();
	}

	public void startDrag() {
		dragging = true;
	}

	@Override
	public void onLayerAdd(GameLayer layer) {
		// 
	}
	
	@Override
	public void onActiveCollide(CollisionGeometryOwner passiveCollider, CollisionGeometry myGeo, CollisionGeometry otherGeo) {
		// 
	}

	@Override
	public void onPassiveCollide(CollisionGeometryOwner activeCollider, CollisionGeometry myGeo, CollisionGeometry otherGeo) {
		// 
	}

	@Override
	public void onActiveMovementCollide(CollisionGeometryOwner passiveCollider, CollisionGeometry myGeo, CollisionGeometry otherGeo) {
		// 
	}

	@Override
	public void onPassiveMovementCollide(CollisionGeometryOwner activeCollider, CollisionGeometry myGeo, CollisionGeometry otherGeo) {
		// 
	}

	@Override
	public boolean canCollideWith(CollisionGeometryOwner other) {
		return false;
	}

	@Override
	public boolean canMoveCollideWith(CollisionGeometryOwner other) {
		return false;
	}

}
