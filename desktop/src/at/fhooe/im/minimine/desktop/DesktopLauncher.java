package at.fhooe.im.minimine.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import at.fhooe.im.minimine.MiniMine;
import at.fhooe.im.minimine.tutorials.CubeTest;
import at.fhooe.im.minimine.tutorials.ShaderTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MiniMine(), config);
		
	}
}
