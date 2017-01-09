package at.fhooe.im.minimine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;

import at.fhooe.im.minimine.tutorials.TestShader;

public class ColorTest extends ApplicationAdapter {
	
	public PerspectiveCamera cam;
	   public CameraInputController camController;
	   public Shader shader;
	   public Model model;
	   public Array<ModelInstance> instances = new Array<ModelInstance>();
	   public ModelBatch modelBatch;

	   @Override
	   public void create () {
	       cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	       cam.position.set(0f, 8f, 8f);
	       cam.lookAt(0,0,0);
	       cam.near = 1f;
	       cam.far = 300f;
	       cam.update();

	       camController = new CameraInputController(cam);
	       Gdx.input.setInputProcessor(camController);

	       ModelBuilder modelBuilder = new ModelBuilder();
	       model = modelBuilder.createSphere(2f, 2f, 2f, 20, 20,
	         new Material(),
	         Usage.Position | Usage.Normal | Usage.TextureCoordinates);

	       for (int x = -5; x <= 5; x+=2) {
	         for (int z = -5; z<=5; z+=2) {
	             instances.add(new ModelInstance(model, x, 0, z));
	         }
	       }

	       shader = new TestShader();
	       shader.init();

	       modelBatch = new ModelBatch();
	   }

	   @Override
	   public void render () {
	    camController.update();

	    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

	    modelBatch.begin(cam);
	    for (ModelInstance instance : instances)
	        modelBatch.render(instance, shader);
	    modelBatch.end();
	   }

	   @Override
	   public void dispose () {
	       shader.dispose();
	       model.dispose();
	       modelBatch.dispose();
	   }

}
