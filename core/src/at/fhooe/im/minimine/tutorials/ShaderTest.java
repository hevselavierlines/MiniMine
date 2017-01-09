package at.fhooe.im.minimine.tutorials;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;

import at.fhooe.im.minimine.render.shader.ColorShader;

public class ShaderTest extends ApplicationAdapter {

	private PerspectiveCamera cam;
	private CameraInputController camController;
	private Shader shader;
	private RenderContext renderContext;
	private Model model;
	private Renderable renderable;
     
    @Override
    public void create () {
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(2f, 2f, 2f);
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
     
        NodePart blockPart = model.nodes.get(0).parts.get(0);
          
        renderable = new Renderable();
        blockPart.setRenderable(renderable);
//        renderable.meshPart.primitiveType = GL20.GL_POINTS; // uncomment to just render the vertices alone
        renderable.environment = null;
        renderable.worldTransform.idt();
          
        renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
        shader = new ColorShader();
        shader.init();
    }
     
    @Override
    public void render () {
        camController.update();
         
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
 
        renderContext.begin();
        shader.begin(cam, renderContext);
        shader.render(renderable);
        shader.end();
        renderContext.end();
    }
     
    @Override
    public void dispose () {
        shader.dispose();
        model.dispose();
}


}
