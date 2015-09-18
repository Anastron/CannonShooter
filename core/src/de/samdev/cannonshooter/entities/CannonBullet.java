package de.samdev.cannonshooter.entities;

import com.badlogic.gdx.graphics.Color;
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
	
	private Cannon cannon;
	private final Team team;
	
	private boolean inBarrel = true;
	public float scale = 0.001f;
	private boolean death = false;
	
	public CannonBullet(Cannon owner, Team t) {
		super(Textures.cannon_bullet, 0.5f, 0.5f);
		this.cannon = owner;
		this.team = t;
		
		setPosition(cannon.getPositionX(), cannon.getPositionY());
		
		setZLayer(ZLayers.LAYER_CANNON_BULLET);
		
		setColorTint(team.teamColor);
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
		
		// TODO OPTIMIZE
		// TODO Add max lifetime (=> kill() )
		if (! owner.getVisibleMapBox().overlaps(new Rectangle(getPositionX(), getPositionY(), getWidth(), getHeight()))) 
		{
			alive = false;
		}
	}
	
	public void shoot(float rotation) {
		inBarrel = false;
		
		speed.set(0.001f, 0);
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
