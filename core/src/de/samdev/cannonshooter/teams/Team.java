package de.samdev.cannonshooter.teams;

import com.badlogic.gdx.graphics.Color;

public class Team {

	public static final Color COL_NEUTRAL = new Color(127/255f, 127/255f, 127/255f, 1.0f);
	public static final Color COL_P1 = new Color(38/255f, 127/255f, 0/255f, 1.0f);
	public static final Color COL_P2 = new Color(255/255f, 0/255f, 0/255f, 1.0f);
	public static final Color COL_P3 = new Color(0/255f, 0/255f, 255/255f, 1.0f);
	public static final Color COL_P4 = new Color(255/255f, 216/255f, 0/255f, 1.0f);
	public static final Color COL_P5 = new Color(0/255f, 255/255f, 255/255f, 1.0f);
	public static final Color COL_P6 = new Color(178/255f, 0/255f, 255/255f, 1.0f);

	public static final float MULTIPLIER_PLAYER = 1; 
	public static final float MULTIPLIER_NEUTRAL = 0.5f; 
	public static final float MULTIPLIER_AI_D0 = 0.80f; 
	public static final float MULTIPLIER_AI_D1 = 0.875f; 
	public static final float MULTIPLIER_AI_D2 = 0.95f; 
	public static final float MULTIPLIER_AI_D3 = 1.0f; 
	
	public final int ID;
	
	public final Color teamColor;

	public final boolean isUserControllable;
	public final boolean isComputerControllable;
	public final boolean isNeutral; //Non-Combatant

	public final float speedMultiplier;
	
	public Team(int id, Color col, boolean user, boolean computer, boolean neutral, float mult) {
		this.ID = id;
		this.teamColor = col;
		this.isUserControllable = user;
		this.isComputerControllable = computer;
		this.isNeutral = neutral;
		this.speedMultiplier = mult;
	}

	public static Team GenerateTeamNeutral() {
		return new Team(0, COL_NEUTRAL, false, true, true, MULTIPLIER_NEUTRAL);
	}

	public static Team GenerateTeamPlayer() {
		return new Team(0, COL_P1, true, false, false, MULTIPLIER_PLAYER);
	}
}
