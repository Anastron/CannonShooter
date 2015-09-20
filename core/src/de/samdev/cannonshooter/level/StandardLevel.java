package de.samdev.cannonshooter.level;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import de.samdev.absgdx.framework.AgdxGame;
import de.samdev.absgdx.framework.layer.GameLayer;
import de.samdev.absgdx.framework.map.TileMap;
import de.samdev.absgdx.framework.map.background.RepeatingBackground;
import de.samdev.absgdx.framework.map.mapscaleresolver.ShowCompleteMapScaleResolver;
import de.samdev.cannonshooter.Textures;
import de.samdev.cannonshooter.entities.Cannon;
import de.samdev.cannonshooter.teams.Team;
import de.samdev.cannonshooter.tiles.StandardTile;

public class StandardLevel extends GameLayer {

	private List<Team> teams = new ArrayList<Team>();
	
	public Team team_neutral = Team.GenerateTeamNeutral();
	public Team team_player = Team.GenerateTeamPlayer();

	private Team team_computer1 = new Team(10, Team.COL_P2, false, true, false, Team.MULTIPLIER_AI_D0);
	private Team team_computer2 = new Team(11, Team.COL_P3, false, true, false, Team.MULTIPLIER_AI_D0);
	private Team team_computer3 = new Team(12, Team.COL_P4, false, true, false, Team.MULTIPLIER_AI_D0);
	
	public StandardLevel(AgdxGame owner) {
		super(owner, TileMap.createEmptyMapUnsafe(70, 40, StandardTile.class));
		
		initTeams();
		
		initMap();
	}

	private void initMap() {
		addBackground(new RepeatingBackground(Textures.texbackground, 1/32f));
		setMapScaleResolver(new ShowCompleteMapScaleResolver());

		addEntity(new Cannon(6, 25, team_player));
		addEntity(new Cannon(48, 6, team_computer1));
		addEntity(new Cannon(60, 34, team_neutral));
	}

	private void initTeams() {
		teams.add(team_neutral);
		teams.add(team_player);

		teams.add(team_computer1);
		teams.add(team_computer2);
		teams.add(team_computer3);
	}
	
	@Override
	public void onResize() {
		super.onResize();
		
		// Center Map
		setRawOffset(new Vector2(-(getVisibleMapBox().width - getMap().width)/2, -(getVisibleMapBox().height - getMap().height)/2));
	}

	@Override
	public void onUpdate(float arg0) {
		//
	}
}
