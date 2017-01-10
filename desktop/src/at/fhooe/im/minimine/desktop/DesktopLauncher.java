package at.fhooe.im.minimine.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import at.fhooe.im.minimine.ColorTest;
import at.fhooe.im.minimine.ManuelTest;
import at.fhooe.im.minimine.MiniMine;
import at.fhooe.im.minimine.StaudiTest;
import at.fhooe.im.minimine.WireFrameRenderTest;
import at.fhooe.im.minimine.meshbuilder.MeshBuilderTest;
import at.fhooe.im.minimine.meshbuilder.MeshBuilderTest2;
import at.fhooe.im.minimine.meshbuilder.MeshBuilderTest3;
import at.fhooe.im.minimine.tutorials.CubeTest;
import at.fhooe.im.minimine.tutorials.ShaderTest;
import at.fhooe.im.minimine.ui.UITest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MeshBuilderTest3(), config);
	}
}
