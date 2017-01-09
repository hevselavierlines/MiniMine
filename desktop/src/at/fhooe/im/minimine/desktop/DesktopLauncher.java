package at.fhooe.im.minimine.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

<<<<<<< HEAD
import at.fhooe.im.minimine.ColorTest;
=======
import at.fhooe.im.minimine.CharacterTest;
>>>>>>> 7719d7ed7bcbcc2f1629080bcf96b4137ed9067f
import at.fhooe.im.minimine.ManuelTest;
import at.fhooe.im.minimine.MiniMine;
import at.fhooe.im.minimine.WireFrameRenderTest;
import at.fhooe.im.minimine.meshbuilder.MeshBuilderTest;
import at.fhooe.im.minimine.tutorials.CubeTest;
import at.fhooe.im.minimine.tutorials.ShaderTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
<<<<<<< HEAD
		new LwjglApplication(new MeshBuilderTest(), config);
=======
		new LwjglApplication(new CharacterTest(), config);
>>>>>>> 7719d7ed7bcbcc2f1629080bcf96b4137ed9067f
		
	}
}
