package at.fhooe.im.minimine;


import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

import at.fhooe.im.minimine.exception.OutOfChunkBoundsException;
import at.fhooe.im.minimine.exception.OverwritingChunkException;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.biome.generator.FlatlandBiomeGenerator;
import at.fhooe.im.minimine.world.block.DirtBlock;



public class ManuelTest extends ApplicationAdapter {
	protected static ShaderProgram createMeshShader() {
		ShaderProgram.pedantic = false;
		ShaderProgram shader = null;
		try {
			shader = new ShaderProgram(Util.readFile(Util.getResourceAsStream("shader/testshader.vertex.glsl")), 
					Util.readFile(Util.getResourceAsStream("shader/testshader.fragment.glsl")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String log = shader.getLog();
		if (!shader.isCompiled())
			throw new GdxRuntimeException(log);		
		if (log!=null && log.length()!=0)
			System.out.println("Shader Log: "+log);
		return shader;
	}
	
	Mesh mesh;
	Camera cam;
	ShaderProgram shader;
	
		
	//Position attribute - (x, y) 
	public static final int POSITION_COMPONENTS = 3;
	
	
	//Total number of components for all attributes
	public static final int NUM_COMPONENTS = POSITION_COMPONENTS;

	//The maximum number of triangles our mesh will hold
	public static final int MAX_TRIS = 10;
	
	//The maximum number of vertices our mesh will hold
	public static final int MAX_VERTS = MAX_TRIS * 3;
	
	
	private CameraInputController camController;
	private WorldRenderer worldRenderer;
	Texture img;
	
	@Override
	public void create() {
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		
		//mesh = genCube(true, true, true, true, true, true);
		World world = new World("MARS");
		
		
		
		try {
			world.addChunk(new FlatlandBiomeGenerator().generateChunk(world, 0, 0));
			world.addChunk(new FlatlandBiomeGenerator().generateChunk(world, 0, 1));
			world.addChunk(new FlatlandBiomeGenerator().generateChunk(world, 0, -1));
		} catch (OverwritingChunkException e) {
			e.printStackTrace();
		}
		worldRenderer = new WorldRenderer(world, 10);
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				worldRenderer.loadMeshAt(j, i);
			}
		}
		
		//mesh = new ChunkMeshGenerator().generateChunk(chunk, 0, 0);
		
		shader = createMeshShader();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 200f, 0f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
        
        img = new Texture("badlogic.jpg");
  
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		//enable blending, for alpha
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		//start the shader before setting any uniforms
		shader.begin();
		
		
		//update the projection matrix so our triangles are rendered in 2D
		shader.setUniformMatrix("u_projTrans", cam.combined);
		
		
		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
		img.bind();

		shader.setUniformi("s_texture", 0);

		worldRenderer.render(shader);
		
		shader.end();
	}


	
	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		mesh.dispose();
		shader.dispose();
	}
}
