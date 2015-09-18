package de.samdev.cannonshooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import de.samdev.absgdx.framework.entities.Entity;
import de.samdev.absgdx.framework.entities.colliosiondetection.CollisionGeometryOwner;
import de.samdev.absgdx.framework.entities.colliosiondetection.geometries.CollisionGeometry;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.cannonshooter.Textures;
import de.samdev.cannonshooter.ZLayers;
import de.samdev.cannonshooter.util.MathUtils;

public class CannonBarrel extends Entity {
	private static final float CHARGE_SPEED = 0.00066f;
	private static final float UNCHARGE_SPEED = 0.001f;
	private static final float ROTATION_SPEED = 0.18f;
	private static final float RECOIL_PERC = 0.035f;
	
	private boolean dragging = false;
	
	private float rotation = 0;
	private float targetRotation = 0;
	private float charge = 0;
	
	private boolean loaded = false;
	private CannonBullet bullet = null;

	private Cannon cannon;
	
	public CannonBarrel(Cannon owner) {
		super(Textures.cannon_barrel[0], 4, 2);
		cannon = owner;
		
		setPosition(owner.getPositionX(), owner.getPositionY());
		
		setZLayer(ZLayers.LAYER_CANNON_BARREL);
	}
	
	@Override
	public TextureRegion getTexture() {
		float realCharge = (charge) / (1 - RECOIL_PERC);
		
		if (charge > (1-RECOIL_PERC)) realCharge = (1 - charge) / RECOIL_PERC;
		
		return Textures.cannon_barrel[31 - Math.min(31, (int)(realCharge * 32))];
	}

	@Override
	public void beforeUpdate(float delta) {
		if (dragging) updateDragging();
		
		updateRotation(delta);
		updateCharge(delta);
		updateBullet();
	}

	private void updateBullet() {
		if (loaded) {
			Vector2 v = new Vector2(1f - 0.5f + (1.9f) * charge, 0);
			
			v.rotate(rotation);
			
			bullet.setPosition(cannon.getCenterX() + v.x - bullet.getWidth()/2, cannon.getCenterY() + v.y - bullet.getHeight()/2);
			bullet.scale = charge;
		}
	}

	private void updateCharge(float delta) {
		if (cannon.health == 0 || cannon.health == 1) {
			if (loaded)
			{
				charge += CHARGE_SPEED * delta * cannon.team.speedMultiplier;
				
				if (charge > 1) {
					charge = 0;	
				
					bullet.shoot(rotation);
					bullet = null;
					loaded = false;
				}
			} else {
				charge = 0;
				
				bullet = new CannonBullet(cannon, cannon.team);
				owner.addEntity(bullet);
				loaded = true;
			}
		} else if (cannon.health <= 0.5) {
			charge -= UNCHARGE_SPEED * delta;

			if (charge < 0)
				charge = 0;
		}
	}

	private void updateRotation(float delta) {
		if (rotation != targetRotation) {
			float sign = MathUtils.moduloSignum(rotation, targetRotation, 360, 1);
			
			rotation += sign * ROTATION_SPEED * delta;
			if (sign != MathUtils.moduloSignum(rotation, targetRotation, 360, 1)) rotation = targetRotation;
			
			rotation = (rotation + 360) % 360;
		}
	}
	
	private void updateDragging() {
		if (! Gdx.input.isTouched()) {
			dragging = false;
			return;
		}
		
		Vector2 mouse = new Vector2(owner.getMouseOnMapPositionX() - cannon.getCenterX(), owner.getMouseOnMapPositionY() - cannon.getCenterY());
		
		targetRotation = mouse.angle();
	}

	@Override
	public float getTextureRotation() {
		return rotation;
	}
	
	public void startDrag() {
		dragging = true;
	}

	public void onTeamChanged() {
		if (bullet != null) bullet.kill();
		
		bullet = null;
		loaded = false;
	}
	
	@Override
	protected void renderTexture(SpriteBatch sbatch, TextureRegion tex, float offsetX, float offsetY) {
		// Cause of the different origin
		sbatch.draw(
				tex, 
				getPositionX() + offsetX, getPositionY() + offsetY, 
				getWidth()/4f, getHeight()/2f, 
				getWidth(), getHeight(), 
				getTextureScaleX(), getTextureScaleY(), 
				getTextureRotation());
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
