package de.samdev.cannonshooter.entities;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;

import de.samdev.absgdx.framework.entities.Entity;
import de.samdev.absgdx.framework.entities.colliosiondetection.CollisionGeometryOwner;
import de.samdev.absgdx.framework.entities.colliosiondetection.geometries.CollisionGeometry;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.cannonshooter.Textures;
import de.samdev.cannonshooter.ZLayers;
import de.samdev.cannonshooter.teams.Team;

public class CannonBullet extends Entity {
	private final static float RESIZE_SPEED = 0.002f;
	private final static float MAX_LIFETIME = 25 * 1000;
	private final static float BULLET_IGNORE_TIME = 50;
	private final static float SHOOTING_SPEED = 0.004f;
	
	public Cannon cannon;
	private final Team team;
	
	private boolean inBarrel = true;
	public float scale = 0.001f;
	private boolean death = false;
	
	public float lifetime = 0;
	
	public Map<CannonBullet, Float> ignoredBullets = new HashMap<CannonBullet, Float>();
	
	public CannonBullet(Cannon owner, Team t) {
		super(Textures.cannon_bullet, 1f, 1f);
		this.cannon = owner;
		this.team = t;
		
		setPosition(cannon.getPositionX(), cannon.getPositionY());
		
		setZLayer(ZLayers.LAYER_CANNON_BULLET);
		
		setColorTint(team.teamColor);
	}
	
	@Override
	public void onLayerAdd(GameLayer layer) {
		addFullCollisionCircle();
	}

	@Override
	public void beforeUpdate(float delta) {
		if (death) {
			scale -= RESIZE_SPEED * delta;
			if (scale <= 0) {
				death = false;
				scale = 0.0001f;
				alive = false;
			}
		}
		
		lifetime += delta;
		
		if (lifetime > MAX_LIFETIME) {
			kill();
		}
		
		Rectangle vbox = owner.getVisibleMapBox();
		if (vbox.x > getPositionRightX() || vbox.y > getPositionTopY() || vbox.x+vbox.width < getPositionX() || vbox.y+vbox.height < getPositionY()) {
			alive = false;
		}
	}
	
	public void shoot(float rotation) {
		inBarrel = false;
		
		speed.set(SHOOTING_SPEED, 0);
		speed.rotate(rotation);
	}

	public void kill() {
		death = true;
	}
	
	@Override
	public float getTextureScaleX() {
		return scale;
	}
	
	@Override
	public float getTextureScaleY() {
		return scale;
	}

	@Override
	public void onActiveCollide(CollisionGeometryOwner passiveCollider, CollisionGeometry myGeo, CollisionGeometry otherGeo) {
		if (passiveCollider == cannon) return;
		if (! alive) return;
		if (inBarrel) return;
		
		if (passiveCollider instanceof CannonBullet) {
			CannonBullet colliderBullet = (CannonBullet)passiveCollider;
			
			if (colliderBullet.inBarrel) {
				colliderBullet.cannon.onBulletHit(this.team);
				
				alive = false;
			} else {

				if (colliderBullet.team != this.team) {
					colliderBullet.alive = false;
					this.alive = false;
					colliderBullet.alive = false;
				} else {
					bounce(colliderBullet);
				}
			}
		}

		if (passiveCollider instanceof Cannon) {
			Cannon cannon = (Cannon)passiveCollider;
			
			cannon.onBulletHit(this.team);

			alive = false;
		}
	}

	private void bounce(CannonBullet other) {
		float ctime = owner.owner.getCurrentGameTimeMillis();
		
		if (this.isIgnored(other, ctime)) return;
		
		this.ignoredBullets.put(other, ctime);
		other.ignoredBullets.put(this, ctime);
		
		float dx = other.getPositionX() - this.getPositionX();
		float dy = other.getPositionY() - this.getPositionY();

		float quadDis = dx * dx + dy * dy;

		float v1d = this.speed.x * dx  + this.speed.y * dy;
		float v2d = other.speed.x * dx + other.speed.y * dy;

		float k1Vx = this.speed.x  - dx * (v1d - v2d) / quadDis;
		float k1Vy = this.speed.y  - dy * (v1d - v2d) / quadDis;
		float k2Vx = other.speed.x - dx * (v2d - v1d) / quadDis;
		float k2Vy = other.speed.y - dy * (v2d - v1d) / quadDis;

		this.speed.set(k1Vx, k1Vy);
		other.speed.set(k2Vx, k2Vy);
	}

	private boolean isIgnored(CannonBullet other, float currentTimeMillis) {
		return this.ignoredBullets.containsKey(other) && currentTimeMillis - this.ignoredBullets.get(other) < BULLET_IGNORE_TIME;
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
		return other instanceof CannonBullet || other instanceof Cannon;
	}

	@Override
	public boolean canMoveCollideWith(CollisionGeometryOwner other) {
		return false;
	}

}
