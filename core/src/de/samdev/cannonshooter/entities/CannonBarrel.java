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
	private static final float CHARGE_SPEED     = 0.00078f;
	private static final float UNCHARGE_SPEED   = 0.001f;
	private static final float ROTATION_SPEED   = 0.175f;
	private static final float RECOIL_PERC      = 0.035f;
	private static final int MAX_BOOSTER_COUNT  = 8;
	private static final float BOOST_PERCENTAGE = 0.5f;
	private static final float BULLET_ANGLE_VARIANCE = 6f;
	
	private boolean dragging = false;
	
	private float rotation = 0;
	private float targetRotation = 0;
	private float charge = 0;
	
	private boolean loaded = false;
	private CannonBullet bullet = null;

	private Cannon cannon;
	
	private float[] booster = new float[MAX_BOOSTER_COUNT];
	
	public CannonBarrel(Cannon owner) {
		super(Textures.cannon_barrel[0], 8, 4);
		cannon = owner;
		
		clearBooster();
		
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
		updateDragging();
		updateRotation(delta);
		updateCharge(delta);
		updateBullet();
		updateBooster(delta);
	}

	private void updateBullet() {
		if (loaded) {
			Vector2 v = new Vector2(1.5f + 3f * charge, 0);
			
			v.rotate(rotation);
			
			bullet.setPosition(cannon.getCenterX() + v.x - bullet.getWidth()/2, cannon.getCenterY() + v.y - bullet.getHeight()/2);
			bullet.scale = charge;
		}
	}

	private void updateCharge(float delta) {
		if (cannon.health == 0 || cannon.health == 1) {
			if (loaded) {
				charge += CHARGE_SPEED * getBoost() * delta * cannon.team.speedMultiplier;
				
				if (charge > 1) {
					charge = 0;	
				
					float shootingAngle = rotation + cannon.Random.nextFloat() * BULLET_ANGLE_VARIANCE - (BULLET_ANGLE_VARIANCE/2f);
					bullet.shoot(shootingAngle);
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
		if (! dragging) return;
		
		if (! Gdx.input.isTouched()) {
			dragging = false;
			return;
		}
		
		Vector2 mouse = new Vector2(owner.getMouseOnMapPositionX() - cannon.getCenterX(), owner.getMouseOnMapPositionY() - cannon.getCenterY());
		
		targetRotation = mouse.angle();
	}
	
	private void updateBooster(float delta) {
		for (int i = 0; i < MAX_BOOSTER_COUNT; i++) {
			if (booster[i] > 0) booster[i] -= delta;
		}
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
		
		clearBooster();
	}
	
	public void clearBooster() {
		for (int i = 0; i < MAX_BOOSTER_COUNT; i++) {
			booster[i] = -1;
		}
	}
	
	public boolean addBooster() {
		for (int i = 0; i < MAX_BOOSTER_COUNT; i++) {
			if (booster[i] < 0) {
				booster[i] = 1 / (CHARGE_SPEED * cannon.team.speedMultiplier); // So a single cannon can always hold up a 0.5x multiplier
				return true;
			}
		}
		
		return false;
	}
	
	public float getBoost() {
		float boost = 1f;
		
		for (int i = 0; i < MAX_BOOSTER_COUNT; i++) {
			if (booster[i] > 0) boost += BOOST_PERCENTAGE;
		}
		return boost;
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
