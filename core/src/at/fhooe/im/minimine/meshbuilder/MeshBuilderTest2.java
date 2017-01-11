package at.fhooe.im.minimine.meshbuilder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

import at.fhooe.im.minimine.WorldRenderer;
import at.fhooe.im.minimine.exception.OverwritingChunkException;
import at.fhooe.im.minimine.render.shader.ColorShader;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.block.DirtBlock;

/**
 * 
 * @author Christine
 *
 */
public class MeshBuilderTest2 extends ApplicationAdapter {

	private PerspectiveCamera cam;
	private CameraInputController camController;
//	private ColorShader shader;
	private RenderContext renderContext;
	private Model model;
	private Renderable renderable;
	
	public ShaderProgram program;
	int u_projTrans;
	int u_worldTrans;
	
	private Mesh mesh;
	Texture texture; 
	

	@Override
	public void create () {
		generateMesh();
		createShaderProgramm();

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(2f, 2f, 2f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);
		

//		NodePart blockPart = model.nodes.get(0).parts.get(0);

		renderable = new Renderable();

//		blockPart.setRenderable(renderable);
		//        renderable.meshPart.primitiveType = GL20.GL_POINTS; // render only the vertices
		//        renderable.meshPart.primitiveType = GL20.GL_LINE_STRIP; // render wireframe
		renderable.environment = null;
		renderable.worldTransform.idt();

		renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
		texture = new Texture("grasstexture.png");
	}

	@Override
	public void render () {
		camController.update();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		//enable blending, for alpha
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		//start the shader before setting any uniforms
		program.begin();
		
		
		//update the projection matrix so our triangles are rendered in 2D
		program.setUniformMatrix("u_projTrans", cam.combined);
		
		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
		texture.bind();

//		program.setUniformi("s_texture", 0);
		if(mesh != null) {
			System.out.println("mesh != null");
			mesh = new ModelBuilder().createBox(5f, 5f, 5f, 
	                new Material(TextureAttribute.createDiffuse(texture)),
	                Usage.Position | Usage.Normal | Usage.TextureCoordinates).meshes.first();
//			mesh = new MeshBuilder()
			mesh.render(program, GL20.GL_TRIANGLES, 0, mesh.getMaxVertices());
		}
		
		program.end();
	}

	@Override
	public void dispose () {
		program.dispose();
//		shader.dispose();
		model.dispose();
	}

	/**
	 * 
	 */
	private void generateMesh() {

		MiniMineMeshGenerator gen = new MiniMineMeshGenerator();     
		model = gen.generateTestModel();
		mesh = gen.buildCube();
		

		//    	World world = new World("westeros");
		//    	Chunk c = new Chunk(0,0);
		//    	c.fillChunkUp(DirtBlock.class, Chunk.MAX_CHUNK_COORD_Y);
		//    	try {
		//			world.addChunk(c);
		//		} catch (OverwritingChunkException e) {
		//			e.printStackTrace();
		//		}
		//    	

		//    	for (int i = 0; i < c.x; i++) {
		//    		for (int j = 0; j < c.y; j++) {
		//    			for (int k = 0; k < c.z; k++) {
		//    				
		//    			}
		//    		}
		//    	}



		System.out.println("Hello World!");
	}
	
	private void createShaderProgramm() {
		String vert = Gdx.files.internal("shader/colorshader.vertex.glsl").readString();
        String frag = Gdx.files.internal("shader/colorshader.fragment.glsl").readString();
        program = new ShaderProgram(vert, frag);
        if (!program.isCompiled())
            throw new GdxRuntimeException(program.getLog());
        u_projTrans = program.getUniformLocation("u_projViewTrans");
        u_worldTrans = program.getUniformLocation("u_worldTrans");
	}

}
