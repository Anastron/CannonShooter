package de.samdev.cannonshooter.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import de.samdev.absgdx.framework.entities.Entity;
import de.samdev.absgdx.framework.entities.colliosiondetection.CollisionGeometryOwner;
import de.samdev.absgdx.framework.entities.colliosiondetection.geometries.CollisionGeometry;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.cannonshooter.Textures;
import de.samdev.cannonshooter.ZLayers;
import de.samdev.cannonshooter.level.StandardLevel;
import de.samdev.cannonshooter.teams.Team;

public class Cannon extends Entity {
	private static final float HEALTH_REGEN_PER_HIT = 0.2f;
	private static final float START_HEALTH_REGEN = 0.000015f;
	private static final float END_HEALTH_REGEN = 0.000105f;

	public final Random Random = new Random();
	
	public Team team;
	
	private CannonBarrel barrel;
	private CannonHearth hearth;

	private StandardLevel level;
	
	public float health; // 1 = active | 0 = neutral
	
	public Cannon(float x, float y, Team t) {
		super(Textures.cannon_body, 4, 4);
		
		setPosition(x, y);
		setZLayer(ZLayers.LAYER_CANNON_BODY);
		
		team = t;
		health = (t.isNeutral) ? 0 : 1;
	}

	@Override
	public void onLayerAdd(GameLayer layer) {
		level = (StandardLevel) layer;
		
		addFullCollisionCircle();
		
		//#####################################################################
		
		barrel = new CannonBarrel(this);
		hearth = new CannonHearth(this);
		
		layer.addEntity(barrel);
		layer.addEntity(hearth);
	}

	@Override
	public void beforeUpdate(float delta) {
		if (owner.owner.settings.debugEnabled.get()) {
			if (isMouseOverEntity() && Gdx.input.isKeyPressed(Keys.DOWN) && ! team.isNeutral) {
				addHealth(-0.01f, level.team_neutral);
			}
			
			if (isMouseOverEntity() && Gdx.input.isKeyPressed(Keys.UP) && ! team.isNeutral) {
				addHealth(+0.01f, level.team_neutral);
			}
		}
		
		//#######################
		
		if (isMouseOverEntity() && Gdx.input.justTouched()) {
			barrel.startDrag();
		}
		
		if (! team.isNeutral && health < 1) {
			addHealth(getHealthRegen(delta), team);
		}
	}
	
	public float getHealthRegen(float delta) {
		return (START_HEALTH_REGEN + (END_HEALTH_REGEN - START_HEALTH_REGEN) * health) * delta; // exponential, bitches
	}

	public void setTeam(Team newteam) {
		if (team != newteam) {
			team = newteam;
			
			barrel.onTeamChanged();
		}
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
		return other instanceof CannonBullet;
	}

	@Override
	public boolean canMoveCollideWith(CollisionGeometryOwner other) {
		return false;
	}

	public void onBulletHit(Team hit_team) {
		if (hit_team.isNeutral) return;
		
		if (hit_team == team && health < 1) {
			addHealth(HEALTH_REGEN_PER_HIT, hit_team);
		} else if (hit_team == team && health == 1) {
			barrel.addBooster();
		} else if (hit_team != team) {
			addHealth(-HEALTH_REGEN_PER_HIT, hit_team);
		}
	}

	private void addHealth(float add, Team hit_team) {
		health += add;
		if (health <= 0) {
			setTeam(hit_team);
			health = -health;
		} else if (health > 1) {
			health = 1;
		}
	}
}
