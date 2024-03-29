package de.samdev.cannonshooter.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import de.samdev.absgdx.framework.entities.Entity;
import de.samdev.absgdx.framework.entities.colliosiondetection.CollisionGeometryOwner;
import de.samdev.absgdx.framework.entities.colliosiondetection.geometries.CollisionGeometry;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.cannonshooter.Textures;
import de.samdev.cannonshooter.ZLayers;

public class CannonHearth extends Entity {
	private static final Color COLOR_HEARTLESS = new Color(0.75f, 0.75f, 0.75f, 1f);
	private static final float ROTATION_SPEED = 0.125f;
	private static final float ROTATION_MODULO = 90;
	
	private float rotation = 0;
	
	private Cannon cannon;
	
	public CannonHearth(Cannon owner) {
		super(Textures.cannon_hearth[0], 4, 4);
		cannon = owner;
		
		setPosition(owner.getPositionX(), owner.getPositionY());
		
		setZLayer(ZLayers.LAYER_CANNON_HEARTH);
	}

	@Override
	public void render(SpriteBatch sbatch, ShapeRenderer srenderer) {
		sbatch.setColor(COLOR_HEARTLESS);

		renderTexture(sbatch, Textures.cannon_hearth[63], 0, 0);

		sbatch.setColor(cannon.team.teamColor);
		
		renderTexture(sbatch, Textures.cannon_hearth[(int)(cannon.health * 63)], 0, 0);

		sbatch.setColor(Color.WHITE);
	}

	@Override
	public void beforeUpdate(float delta) {
		if (cannon.health < 1) {
			if (rotation != 0 && cannon.health > 0) {
				rotation = (rotation - delta * ROTATION_SPEED * cannon.team.speedMultiplier * cannon.getBoost());
				if (rotation < 0) rotation = 0;
			}

			if (cannon.health == 0){
				rotation = (rotation - delta * ROTATION_SPEED * cannon.team.speedMultiplier * cannon.getBoost());
				if (rotation < 0) rotation += ROTATION_MODULO;
			}
		} else {
			rotation = (rotation - delta * ROTATION_SPEED * cannon.team.speedMultiplier * cannon.getBoost());
			if (rotation < 0) rotation += ROTATION_MODULO;
		}
	}
	
	@Override
	public float getTextureRotation() {
		return rotation;
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
