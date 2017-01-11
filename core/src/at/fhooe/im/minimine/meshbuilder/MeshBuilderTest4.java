package at.fhooe.im.minimine.meshbuilder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
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
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MeshBuilderTest4 extends ApplicationAdapter {
	
	public PerspectiveCamera cam;
    public CameraInputController camController;
    public Shader shader;
    public RenderContext renderContext;
    public Model model;
    public Renderable renderable;
    
    private Mesh mesh;
    private ShaderProgram shaderProgram;
    private Texture texture;
     
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
     
//        ModelBuilder modelBuilder = new ModelBuilder();
//        model = modelBuilder.createSphere(2f, 2f, 2f, 20, 20, 
//          new Material(),
//          Usage.Position | Usage.Normal | Usage.TextureCoordinates);
//     
//        NodePart blockPart = model.nodes.get(0).parts.get(0);
          
//        renderable = new Renderable();
//        blockPart.setRenderable(renderable);
//        renderable.environment = null;
//        renderable.worldTransform.idt();
          
        renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
        String vert = Gdx.files.internal("shader/shaderB.vertex.glsl").readString();
        String frag = Gdx.files.internal("shader/shaderB.fragment.glsl").readString();
        shaderProgram = new ShaderProgram(vert, frag);
//        shader = new DefaultShader(renderable, new DefaultShader.Config(vert, frag));
//        shader.init();
        
        mesh = new Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.ColorUnpacked(), VertexAttribute.TexCoords(0));
        mesh.setVertices(new float[] 
        {-0.5f, -0.5f, 0, 1, 1, 1, 1, 0, 1,
        0.5f, -0.5f, 0, 1, 1, 1, 1, 1, 1,
        0.5f, 0.5f, 0, 1, 1, 1, 1, 1, 0,
        -0.5f, 0.5f, 0, 1, 1, 1, 1, 0, 0});
        mesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});
        texture = new Texture(Gdx.files.internal("grasstexture.png"));
    }
     
    @Override
    public void render () {
        camController.update();
         
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
 
//        renderContext.begin();
//        shader.begin(cam, renderContext);
//        shader.render(renderable);
//        shader.end();
//        renderContext.end();
        
        renderContext.begin();
        shaderProgram.begin();
        mesh.render(shaderProgram, GL20.GL_TRIANGLES);
        shaderProgram.end();
        renderContext.end();
    }
     
    @Override
    public void dispose () {
//        shader.dispose();
//    	model.dispose();
    	shaderProgram.dispose();
        mesh.dispose();
    }

}
