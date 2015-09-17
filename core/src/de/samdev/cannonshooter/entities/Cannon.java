package de.samdev.cannonshooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;

import de.samdev.absgdx.framework.entities.Entity;
import de.samdev.absgdx.framework.entities.colliosiondetection.CollisionGeometryOwner;
import de.samdev.absgdx.framework.entities.colliosiondetection.geometries.CollisionGeometry;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.cannonshooter.Textures;
import de.samdev.cannonshooter.ZLayers;

public class Cannon extends Entity {

	private CannonBarrel barrel;
	private CannonHearth hearth;
	
	public float power = 1f; // 1 = active | 0 = neutral
	
	public Cannon(float x, float y) {
		super(Textures.cannon_body, 2, 2);
		
		setPosition(x, y);

		setZLayer(ZLayers.LAYER_CANNON_BODY);
	}

	@Override
	public void onLayerAdd(GameLayer layer) {
		barrel = new CannonBarrel(this);
		hearth = new CannonHearth(this);
		
		layer.addEntity(barrel);
		layer.addEntity(hearth);
	}

	@Override
	public void beforeUpdate(float delta) {
		if (isMouseOverEntity() && Gdx.input.justTouched() && Gdx.input.isButtonPressed(Buttons.LEFT) && power > 0)
		{
			power -= 0.1;
		}
		
		if (isMouseOverEntity() && Gdx.input.justTouched() && Gdx.input.isButtonPressed(Buttons.RIGHT) && power < 1)
		{
			power += 0.1;
		}
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

}
