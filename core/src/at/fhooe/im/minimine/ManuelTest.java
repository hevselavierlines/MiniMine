package at.fhooe.im.minimine;


import java.io.IOException;
import java.net.DatagramSocket;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FloatFrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.GdxRuntimeException;

import at.fhooe.im.minimine.exception.OutOfChunkBoundsException;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.block.AbstractBlock;
import at.fhooe.im.minimine.world.block.AirBlock;



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
	
	//The array which holds all the data, interleaved like so:
	//    x, y, r, g, b, a
	//    x, y, r, g, b, a, 
	//    x, y, r, g, b, a, 
	//    ... etc ...
	private float[] verts = new float[MAX_VERTS * NUM_COMPONENTS];
	
	//The index position
	private int idx = 0;
	private CameraInputController camController;
	Texture img;
	
	@Override
	public void create() {
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		
		mesh = genCube(true, true, true, true, true, true);
		
		shader = createMeshShader();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 2f, 2f);
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

		//render the mesh
		mesh.render(shader, GL20.GL_TRIANGLES, 0, mesh.getNumVertices());
		
		shader.end();
	}
	
	public static Mesh generateChunk(Chunk _chunk) {
		
		for(int x = -16; x <= Chunk.MAX_CHUNK_COORD_XZ; x++) {
			for(int y = 0; y <= Chunk.MAX_CHUNK_COORD_Y; y++) {
				for(int z = -16; z <= Chunk.MAX_CHUNK_COORD_XZ; z++) {
					try {
						if(_chunk.getBlockTypeAtChunkCoord(x, y, z) != AirBlock.class) {
							//check above
							if(y < 0 || y >= Chunk.MAX_CHUNK_COORD_Y || _chunk.getBlockTypeAtChunkCoord(x, y - 1, z) == AirBlock.class) {
								//draw the top side
								
							}
						}
						
					} catch (OutOfChunkBoundsException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	public static Mesh genCube (boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {
		
		Mesh mesh = new Mesh(true, Short.MAX_VALUE, 0, new VertexAttribute(Usage.Position, 3, "a_position"), new VertexAttribute(Usage.Normal,
			3, "a_normal"), new VertexAttribute(Usage.TextureCoordinates, 2, "a_texcoords"));
		float xpos = 0f;
		float ypos = 0f;
		float zpos = 0f;
		float[] meshparts = {
				
			//bottom-side
				xpos - 0.5f, ypos - 0.5f, zpos -0.5f,//vertice[0]
				 0.0f, -1.0f, 0.0f, //normals[0]
				 0.0f, 0.0f,		//uv[0]
				 
				 xpos + 0.5f, ypos - 0.5f, zpos + 0.5f, //vertice[2]
				 0.0f, -1.0f, 0.0f, //normals[2]
				 0.2f, 0.2f, 		//uv[2]
				 
				 xpos - 0.5f, ypos - 0.5f, zpos + 0.5f,//vertice[1]
				 0.0f, -1.0f, 0.0f, //normals[1]
				 0.0f, 0.2f, 		//uv[1]
				 
				 xpos - 0.5f, ypos -0.5f, zpos -0.5f,//vertice[0]
				 0.0f, -1.0f, 0.0f, //normals[0]
				 0.0f, 0.0f,		//uv[0]
				 
				 xpos +0.5f, ypos -0.5f, zpos -0.5f, //vertice[3]
				 0.0f, -1.0f, 0.0f, //normals[0]
				 0.2f, 0.0f,		//uv[3]
				 
				 xpos +0.5f, ypos-0.5f, zpos+0.5f, //vertice[2]
				 0.0f, -1.0f, 0.0f, //normals[2]
				 0.2f, 0.2f, 		//uv[2]
				 
				//top-side
				 
				 xpos -0.5f, ypos + 0.5f, zpos -0.5f,//4
				 0.0f, 1.0f, 0.0f,
				 0.4f, 0.2f,
				 
				 xpos -0.5f, ypos + 0.5f, zpos + 0.5f, //5
				 0.0f, 1.0f, 0.0f,
				 0.4f, 0.4f,
				 
				 xpos +0.5f, ypos +0.5f, zpos+ 0.5f, 	//6
				 0.0f, 1.0f, 0.0f, 
				 0.2f, 0.4f,
				 
				 xpos -0.5f, ypos + 0.5f, zpos -0.5f,//4
				 0.0f, 1.0f, 0.0f,
				 0.4f, 0.2f,
				 
				 xpos +0.5f, ypos +0.5f, zpos+ 0.5f, 	//6
				 0.0f, 1.0f, 0.0f, 
				 0.2f, 0.4f,
				 
				 xpos +0.5f, ypos +0.5f, zpos -0.5f,	//7
				 0.0f, 1.0f, 0.0f,
				 0.2f, 0.2f,
				 
				 //front
				 
				 xpos-0.5f, ypos-0.5f, zpos-0.5f, 	//8
				 0.0f, 0.0f, -1.0f, 
				 0.4f, 0.4f, //8
				 
				xpos-0.5f, ypos+0.5f, zpos-0.5f, 	//9
				0.0f, 0.0f, -1.0f, 
				0.4f, 0.6f, //9
				
				xpos+0.5f, ypos+0.5f, zpos-0.5f,	//10
				0.0f, 0.0f, -1.0f, 
				0.6f, 0.6f, //10
				
				xpos-0.5f, ypos-0.5f, zpos-0.5f, 	//8
				0.0f, 0.0f, -1.0f, 
				0.4f, 0.4f, //8
				 
				xpos+0.5f, ypos+0.5f, zpos-0.5f,	//10
				0.0f, 0.0f, -1.0f, 
				0.6f, 0.6f, //10
				
				xpos+0.5f, ypos-0.5f, zpos-0.5f, 	//11
				0.0f, 0.0f, -1.0f, 
				0.6f, 0.4f,
				
				//back
				xpos-0.5f, ypos-0.5f, zpos+0.5f, 	//12
				0.0f, 0.0f, 1.0f, 
				0.4f, 0.4f, //12
				
				xpos+0.5f, ypos-0.5f, zpos+0.5f, 		//15
				0.0f, 0.0f, 1.0f, 
				0.4f, 0.6f,	//15
				
				xpos+0.5f, ypos+0.5f, zpos+0.5f, 		//14
				0.0f, 0.0f, 1.0f, 
				0.6f, 0.6f, //14
				
				xpos-0.5f, ypos-0.5f, zpos+0.5f, 	//12
				0.0f, 0.0f, 1.0f, 
				0.4f, 0.4f, //12
				
				xpos+0.5f, ypos+0.5f, zpos+0.5f, 		//14
				0.0f, 0.0f, 1.0f, 
				0.6f, 0.6f, //14
				
				xpos-0.5f, ypos+0.5f, zpos+0.5f, 		//13
				0.0f, 0.0f, 1.0f, 
				0.6f, 0.4f, //13
				
				//left
				xpos-0.5f, ypos-0.5f, zpos-0.5f,	//16
				-1.0f, 0.0f, 0.0f,
				0.6f, 0.6f,	//16 
				
				xpos-0.5f, ypos-0.5f, zpos+0.5f, 	//17
				-1.0f, 0.0f, 0.0f,
				0.6f, 0.8f, //17
				
				xpos-0.5f, ypos+0.5f, zpos+0.5f, 		//18
				-1.0f, 0.0f, 0.0f,
				0.8f, 0.8f, //18
				
				xpos-0.5f, ypos-0.5f, zpos-0.5f,	//16
				-1.0f, 0.0f, 0.0f,
				0.6f, 0.6f,	//16 
				
				xpos-0.5f, ypos+0.5f, zpos+0.5f, 		//18
				-1.0f, 0.0f, 0.0f,
				0.8f, 0.8f, //18
				
				xpos-0.5f, ypos+0.5f, zpos-0.5f, 	//19
				-1.0f, 0.0f, 0.0f,
				0.8f, 0.6f, //19
				
				//bottom
				
				xpos+0.5f, ypos-0.5f, zpos-0.5f, 	//20
				1.0f, 0.0f, 0.0f, 
				0.8f, 0.8f, //20
				
				xpos+0.5f, ypos+0.5f, zpos-0.5f,		//23
				1.0f, 0.0f, 0.0f,
				1.0f, 0.8f, //23
				
				xpos+0.5f, ypos+0.5f, zpos+0.5f,		//22
				1.0f, 0.0f, 0.0f, 
				1.0f, 1.0f,//22
				
				xpos+0.5f, ypos-0.5f, zpos-0.5f, 	//20
				1.0f, 0.0f, 0.0f, 
				0.8f, 0.8f, //20
				
				xpos+0.5f, ypos+0.5f, zpos+0.5f,		//22
				1.0f, 0.0f, 0.0f, 
				1.0f, 1.0f,//22
				
				xpos+0.5f, ypos-0.5f, zpos+0.5f, 		//21
				1.0f, 0.0f, 0.0f, 
				0.8f, 1.0f //21
		};
		float[] cubeVerts = {
				-0.5f, -0.5f, -0.5f,//0
				-0.5f, -0.5f, 0.5f,	//1
				0.5f, -0.5f, 0.5f, 	//2
				0.5f, -0.5f, -0.5f, //3
				-0.5f, 0.5f, -0.5f, //4
			-0.5f, 0.5f, 0.5f, 		//5
			0.5f, 0.5f, 0.5f, 		//6
			0.5f, 0.5f, -0.5f, 		//7
			-0.5f, -0.5f, -0.5f, 	//8
			-0.5f, 0.5f, -0.5f, 	//9
			0.5f, 0.5f, -0.5f,		//10
			0.5f, -0.5f, -0.5f, 	//11
			-0.5f, -0.5f, 0.5f, 	//12
			-0.5f, 0.5f, 0.5f, 		//13
			0.5f, 0.5f, 0.5f, 		//14
			0.5f, -0.5f, 0.5f, 		//15
			-0.5f, -0.5f, -0.5f,	//16
			-0.5f, -0.5f, 0.5f, 	//17
			-0.5f, 0.5f, 0.5f, 		//18
			-0.5f, 0.5f, -0.5f, 	//19
			0.5f, -0.5f, -0.5f, 	//20
			0.5f, -0.5f, 0.5f, 		//21
			0.5f, 0.5f, 0.5f,		//22
			0.5f, 0.5f, -0.5f};		//23

		float[] cubeNormals = {
				0.0f, -1.0f, 0.0f, 
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f, 
				0.0f, -1.0f, 0.0f, 
				0.0f, 1.0f, 0.0f, 
				0.0f, 1.0f, 0.0f, 
				0.0f, 1.0f, 0.0f, 
				0.0f, 1.0f, 0.0f, 
				0.0f, 0.0f, -1.0f, 
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f, 
				0.0f, 0.0f, -1.0f, 
				0.0f, 0.0f, 1.0f, 
				0.0f, 0.0f, 1.0f, 
				0.0f, 0.0f, 1.0f, 
				0.0f, 0.0f, 1.0f, 
				-1.0f, 0.0f, 0.0f, 
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f, 
				-1.0f, 0.0f, 0.0f, 
				1.0f, 0.0f, 0.0f, 
				1.0f, 0.0f, 0.0f, 
				1.0f, 0.0f, 0.0f, 
				1.0f, 0.0f, 0.0f};

		float[] cubeTex = {
				0.0f, 0.0f, //0
				0.0f, 1.0f, //1
				1.0f, 1.0f, //2
				1.0f, 0.0f, //3
				1.0f, 0.0f, //4
				1.0f, 1.0f, //5
				0.0f, 1.0f, //6
				0.0f, 0.0f, //7
				0.0f, 0.0f, //8
				0.0f, 1.0f, //9
				1.0f, 1.0f, //10
				1.0f, 0.0f, //11
				0.0f, 0.0f, //12
				0.0f, 1.0f, //13
				1.0f, 1.0f, //14
				1.0f, 0.0f,	//15
				0.0f, 0.0f,	//16 
				0.0f, 1.0f, //17
				1.0f, 1.0f, //18
				1.0f, 0.0f, //19
				0.0f, 0.0f, //20
				0.0f, 1.0f, //21
				1.0f, 1.0f, //22
				1.0f, 0.0f};//23

		float[] vertices = new float[24 * 8];
		int pIdx = 0;
		int nIdx = 0;
		int tIdx = 0;
		for (int i = 0; i < vertices.length;) {
			vertices[i++] = cubeVerts[pIdx++];
			vertices[i++] = cubeVerts[pIdx++];
			vertices[i++] = cubeVerts[pIdx++];
			vertices[i++] = cubeNormals[nIdx++];
			vertices[i++] = cubeNormals[nIdx++];
			vertices[i++] = cubeNormals[nIdx++];
			vertices[i++] = cubeTex[tIdx++];
			vertices[i++] = cubeTex[tIdx++];
		}
		int indexCount = 0;
		if(front) {
			indexCount += 6;
		}
		if(back) {
			indexCount += 6;
		}
		if(top) {
			indexCount += 6;
		}
		if(bottom) {
			indexCount += 6;
		}
		if(left) {
			indexCount += 6;
		}
		if(right) {
			indexCount += 6;
		}
		
		short[] indices = new short[indexCount];
		int i = 0;
		if(bottom) {
			indices[i++] = 0;
			indices[i++] = 2;
			indices[i++] = 1;
			indices[i++] = 0;
			indices[i++] = 3;
			indices[i++] = 2;
		}
		if(top) {
			indices[i++] = 4;
			indices[i++] = 5;
			indices[i++] = 6;
			indices[i++] = 4;
			indices[i++] = 6;
			indices[i++] = 7;
		}
		if(front) {
			indices[i++] = 8;
			indices[i++] = 9;
			indices[i++] = 10;
			indices[i++] = 8;
			indices[i++] = 10;
			indices[i++] = 11;
		}
		if(back) {
			indices[i++] = 12;
			indices[i++] = 15;
			indices[i++] = 14;
			indices[i++] = 12;
			indices[i++] = 14;
			indices[i++] = 13;
		}
		if(left) {
			indices[i++] = 16;
			indices[i++] = 17;
			indices[i++] = 18;
			indices[i++] = 16;
			indices[i++] = 18;
			indices[i++] = 19;
		}
		if(right) {
			indices[i++] = 20;
			indices[i++] = 23;
			indices[i++] = 22;
			indices[i++] = 20;
			indices[i++] = 22;
			indices[i++] = 21;
		}
		//20, 23, 22, 20, 22, 21};
		//short[] indices = {0, 2, 1, 0, 3, 2, 4, 5, 6, 4, 6, 7, 8, 9, 10, 8, 10, 11, 12, 15, 14, 12, 14, 13, 16, 17, 18, 16, 18, 19,
		//	20, 23, 22, 20, 22, 21};
		mesh.setVertices(meshparts);
		//mesh.setVertices(vertices);
		//mesh.setIndices(indices);

		return mesh;
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
