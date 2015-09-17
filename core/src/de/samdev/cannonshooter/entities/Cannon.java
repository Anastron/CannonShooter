package de.samdev.cannonshooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import de.samdev.absgdx.framework.entities.Entity;
import de.samdev.absgdx.framework.entities.colliosiondetection.CollisionGeometryOwner;
import de.samdev.absgdx.framework.entities.colliosiondetection.geometries.CollisionGeometry;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.cannonshooter.Textures;
import de.samdev.cannonshooter.ZLayers;
import de.samdev.cannonshooter.teams.Team;

public class Cannon extends Entity {
	public Team team;
	
	private CannonBarrel barrel;
	private CannonHearth hearth;
	
	public float health; // 1 = active | 0 = neutral
	
	public Cannon(float x, float y, Team t) {
		super(Textures.cannon_body, 2, 2);
		
		setPosition(x, y);
		setZLayer(ZLayers.LAYER_CANNON_BODY);
		
		team = t;
		health = (t.isNeutral) ? 0 : 1;
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
		if (owner.owner.settings.debugEnabled.get())
		{
			if (isMouseOverEntity() && Gdx.input.isKeyPressed(Keys.DOWN) && ! team.isNeutral)
			{
				health = Math.max(0, health - 0.01f);
			}
			
			if (isMouseOverEntity() && Gdx.input.isKeyPressed(Keys.UP) && ! team.isNeutral)
			{
				health = Math.min(1, health + 0.01f);
			}
		}
		
		if (isMouseOverEntity() && Gdx.input.justTouched())
		{
			barrel.startDrag();
		}
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
		return false;
	}

	@Override
	public boolean canMoveCollideWith(CollisionGeometryOwner other) {
		return false;
	}

}
