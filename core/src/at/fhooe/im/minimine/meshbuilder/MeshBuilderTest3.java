package at.fhooe.im.minimine.meshbuilder;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

import at.fhooe.im.minimine.Util;
import at.fhooe.im.minimine.WorldRenderer;
import at.fhooe.im.minimine.exception.OverwritingChunkException;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.biome.Biomes;
import at.fhooe.im.minimine.world.block.DirtBlock;

public class MeshBuilderTest3 extends ApplicationAdapter {

	Mesh mesh;
	Camera cam;
	ShaderProgram shaderProgram;
	private CameraInputController camController;
//	private WorldRenderer worldRenderer;
	private final float[] LIGHT_POS = new float[] {0.0f, 255.0f, 0.0f};
	private final float[] SPECULAR_COLOR = new float[] {0.8f, 0.8f, 0.8f};
	private final float[] DIFFUSE_COLOR = new float[] {1.0f, 1.0f, 1.0f};
	

	//Position attribute - (x, y) 
	public static final int POSITION_COMPONENTS = 3;


	//Total number of components for all attributes
	public static final int NUM_COMPONENTS = POSITION_COMPONENTS;

	//The maximum number of triangles our mesh will hold
	public static final int MAX_TRIS = 10;

	//The maximum number of vertices our mesh will hold
	public static final int MAX_VERTS = MAX_TRIS * 3;

	private Texture texture;
	private int u_projTrans;
	private int u_worldTrans;
	public Renderable renderable;


//	protected static ShaderProgram createMeshShader() {
//		ShaderProgram.pedantic = false;
//		ShaderProgram shader = null;
//		try {
////			shader = new ShaderProgram(Util.readFile(Util.getResourceAsStream("shader/testshader.vertex.glsl")), 
////					Util.readFile(Util.getResourceAsStream("shader/testshader.fragment.glsl")));
//			shader = new ShaderProgram(Util.readFile(Util.getResourceAsStream("shader/colorshader.vertex.glsl")), 
//					Util.readFile(Util.getResourceAsStream("shader/colorshader.fragment.glsl")));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String log = shader.getLog();
//		if (!shader.isCompiled())
//			throw new GdxRuntimeException(log);		
//		if (log!=null && log.length()!=0)
//			System.out.println("Shader Log: "+log);
//		return shader;
//	}



	@Override
	public void create() {
		String vert = Gdx.files.internal("shader/shaderB.vertex.glsl").readString();
		String frag = Gdx.files.internal("shader/shaderB.fragment.glsl").readString();
        shaderProgram = new ShaderProgram(vert, frag);
        
        mesh = new Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.ColorUnpacked(), VertexAttribute.TexCoords(0));
        mesh.setVertices(new float[] 
        {-0.5f, -0.5f, 0, 1, 1, 1, 1, 0, 1,
        0.5f, -0.5f, 0, 1, 1, 1, 1, 1, 1,
        0.5f, 0.5f, 0, 1, 1, 1, 1, 1, 0,
        -0.5f, 0.5f, 0, 1, 1, 1, 1, 0, 0});
        mesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});
        texture = new Texture(Gdx.files.internal("grasstexture.png"));
		
//		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

//		MiniMineMeshGenerator generator = new MiniMineMeshGenerator();
//		mesh = generator.buildCube();
		
		//mesh = genCube(true, true, true, true, true, true);
//		World world = new World("MARS");
//
//		try {
//			Chunk chunk1 = new Chunk(0, -2, Biomes.FLATLAND);
//			chunk1.fillChunkUp(DirtBlock.class, 10);
//			world.addChunk(chunk1);
//
//			Chunk chunk2 = new Chunk(0, -1, Biomes.FLATLAND);
//			chunk2.fillChunkUp(DirtBlock.class, 20);
//			world.addChunk(chunk2);
//
//			Chunk chunk3 = new Chunk(0, 0, Biomes.FLATLAND);
//			chunk3.fillChunkUp(DirtBlock.class, 30);
//			world.addChunk(chunk3);
//
//			Chunk chunk4 = new Chunk(0, 1, Biomes.FLATLAND);
//			chunk4.fillChunkUp(DirtBlock.class, 40);
//			world.addChunk(chunk4);
//
//			Chunk chunk5 = new Chunk(0, 2, Biomes.FLATLAND);
//			chunk5.fillChunkUp(DirtBlock.class, 50);
//			world.addChunk(chunk5);
//
//			//			world.addChunk(new FlatlandBiomeGenerator().generateChunk(world, 0, -2));
//			//			world.addChunk(new FlatlandBiomeGenerator().generateChunk(world, 0, 1));
//			//			world.addChunk(new FlatlandBiomeGenerator().generateChunk(world, 0, -1));
//			//			world.addChunk(new FlatlandBiomeGenerator().generateChunk(world, 0, 0));
//
//		} catch (OverwritingChunkException e) {
//			e.printStackTrace();
//		}
//		worldRenderer = new WorldRenderer(world, 10);
//		for(int i = -2; i <= 2; i++) {
//			worldRenderer.loadMeshAt(0, i);
//		}
//		worldRenderer.loadMeshAt(0, 0);
		//		for(int i = -1; i <= 1; i++) {
		//			worldRenderer.loadMeshAt(0, i);
		//		}

		//mesh = new ChunkMeshGenerator().generateChunk(chunk, 0, 0);

//		shaderProgram = createMeshShader();
//		initShader();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

//		texture = new Texture("grasstexture.png");
	}


	@Override
	public void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		//enable blending, for alpha
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		//start the shader before setting any uniforms
		shaderProgram.begin();


		//update the projection matrix so our triangles are rendered in 2D
//		shaderProgram.setUniformMatrix("u_projTrans", cam.combined);

		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
		texture.bind();

//		shaderProgram.setUniformi("s_texture", 0);
//		shaderProgram.setUniform3fv("u_lightPos", LIGHT_POS, 0, 3);
//		shaderProgram.setUniform3fv("u_diffuseColor", DIFFUSE_COLOR, 0, 3);
//		shaderProgram.setUniform3fv("u_specularColor", SPECULAR_COLOR, 0, 3);
		
//		worldRenderer.render(shader);
//		shaderProgram.setUniformMatrix(u_projTrans, cam.combined);
		shaderProgram.setUniformMatrix(u_projTrans, cam.combined);
		
		renderable = new Renderable();
		renderable.worldTransform.idt();
		
		shaderProgram.setUniformMatrix(u_worldTrans, renderable.worldTransform);
		mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, mesh.getMaxVertices());

		shaderProgram.end();
		
		/*Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		//enable blending, for alpha
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		shaderProgram.begin();
		
		img.bind();
		
		shaderProgram.setUniformMatrix(u_projTrans, cam.combined);
//		context.setDepthTest(GL20.GL_LEQUAL);
//		context.setCullFace(GL20.GL_BACK);
		
		mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, mesh.getMaxVertices());
		
		shaderProgram.end();*/
		
	/*	texture.bind();
		shaderProgram.begin();
		shaderProgram.setUniformMatrix("u_worldView", cam.combined);
		shaderProgram.setUniformi("u_texture", 0);
		mesh.render(shaderProgram, GL20.GL_TRIANGLES);
		shaderProgram.end();*/
	}

	@Override
	public void dispose() {
		if(mesh != null) {
			mesh.dispose();
		}
		shaderProgram.dispose();
	}
	
	private void initShader() {
		ShaderProgram.pedantic = false;
		
//		String vert = Gdx.files.internal("shader/testshader.vertex.glsl").readString();
//		String frag = Gdx.files.internal("shader/testshader.fragment.glsl").readString();
		String vert = Gdx.files.internal("shader/colorshader.vertex.glsl").readString();
		String frag = Gdx.files.internal("shader/colorshader.fragment.glsl").readString();
//		String vert = Gdx.files.internal("shader/shaderB.vertex.glsl").readString();
//		String frag = Gdx.files.internal("shader/shaderB.fragment.glsl").readString();
        shaderProgram = new ShaderProgram(vert, frag);
        
        if (!shaderProgram.isCompiled()) {
        	throw new GdxRuntimeException(shaderProgram.getLog());
        }
        u_projTrans = shaderProgram.getUniformLocation("u_projViewTrans");
        u_worldTrans = shaderProgram.getUniformLocation("u_worldTrans");
        
        String log = shaderProgram.getLog();
		if (!shaderProgram.isCompiled()) {
			throw new GdxRuntimeException(log);		
		}
		if (log!=null && log.length() != 0) {
			System.out.println("Shader Log: "+log);
		}
		
	}

}
