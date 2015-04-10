package de.samdev.cannonshooter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.samdev.absgdx.framework.util.AndroidResolutions;
import de.samdev.cannonshooter.CannonGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width  = AndroidResolutions.RES__16_9.x;
		config.height = AndroidResolutions.RES__16_9.y;
		
		new LwjglApplication(new CannonGame(), config);
	}
}
