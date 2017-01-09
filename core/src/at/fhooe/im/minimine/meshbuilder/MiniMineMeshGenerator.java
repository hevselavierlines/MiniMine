package at.fhooe.im.minimine.meshbuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

import at.fhooe.im.minimine.exception.ChunkNotExistingException;
import at.fhooe.im.minimine.exception.OutOfChunkBoundsException;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.block.AirBlock;

/**
 * 
 * @author Christine
 * 
 * For the mesh generation the OpenGL coordinate system is used.  
 * OpenGL uses a right-handed coordinate system, with positive x to the right,
 * positive y up and positive z comes out of the screen.
 *
 */
public class MiniMineMeshGenerator {

	/** 
	 * Represents the normals of the faces
	 */
	private final Vector3 nFront = new Vector3(0, 0, -1);
	private final Vector3 nBack = new Vector3(0, 0, 1);
	private final Vector3 nBottom = new Vector3(0, -1, 0);
	private final Vector3 nTop = new Vector3(0, 1, 0);
	private final Vector3 nLeft = new Vector3(-1, 0, 0);
	private final Vector3 nRight = new Vector3(1, 0, 0);

	private float blockSize = 1;

	/**
	 * Represents local transforms of the points of a block
	 */
	private Vector3 vA, vB, vC, vD, vE, vF, vG, vH;



	/*
	 * TODO MiniMineQuadVtxNrmUvColBuilder für Ambient Occlusion 
	 * 
	 */

	/**
	 * 
	 */
	public MiniMineMeshGenerator() {
		rebuildVectors();
	}

	public void setBlockSize(float size) {
		blockSize = size;
		rebuildVectors();
	}

	private void rebuildVectors() {		
		float halfBlockSize = blockSize/2; 
		vA = new Vector3(-halfBlockSize, -halfBlockSize, +halfBlockSize);
		vB = new Vector3(+halfBlockSize, -halfBlockSize, +halfBlockSize);
		vC = new Vector3(+halfBlockSize, +halfBlockSize, +halfBlockSize);
		vD = new Vector3(-halfBlockSize, +halfBlockSize, +halfBlockSize);
		vE = new Vector3(-halfBlockSize, -halfBlockSize, -halfBlockSize);
		vF = new Vector3(+halfBlockSize, -halfBlockSize, -halfBlockSize);
		vG = new Vector3(+halfBlockSize, +halfBlockSize, -halfBlockSize);
		vH = new Vector3(-halfBlockSize, +halfBlockSize, -halfBlockSize);
	}

	/**
	 * 
	 * @param world
	 * @return
	 */
	public Mesh createMesh(World world) {

		MeshBuilder mBuilder = new MeshBuilder();

		for (int x = -16; x <= Chunk.MAX_CHUNK_COORD_XZ; x++) {
			for (int y = 0; y <= Chunk.MAX_CHUNK_COORD_Y; y++) {
				for (int z = -16; z <= Chunk.MAX_CHUNK_COORD_XZ; z++) {


				}
			}
		}

		//		mBuilder.begin(attributes, primitiveType);
		//		mBuilder.rect(corner00, corner10, corner11, corner01, normal);
		//		mBuilder.rect(corner00, corner10, corner11, corner01, normal);
		//		Mesh mesh = mBuilder.end();

		Mesh mesh = new Mesh( true, 4, 0,  // static mesh with 4 vertices and no indices
				new VertexAttribute( Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE ),
				new VertexAttribute( Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE+"0" ) );

		//		mesh.render(shader, primitiveType); 

		return mesh;
	}

	/**
	 * 
	 * @param world
	 * @return
	 */
	public MiniMineMesh createMiniMineMesh(World world) {

		MiniMineQuadVtxBuilder mBuilder = new MiniMineQuadVtxBuilder();

		//		for (int n = 0; n < world.getChunks)
		// load current chunk and surrounding chunks --> need to be 

		Chunk chunk = null;
		try {
			int xPos = 0;
			int zPos = 0;
			chunk = world.getChunk(xPos, zPos);

			int absX = xPos * Chunk.CHUNK_SIZE_XZ;
			int absZ = zPos * Chunk.CHUNK_SIZE_XZ;

			if (chunk != null) {
				for (int x = -16; x <= Chunk.MAX_CHUNK_COORD_XZ; x++) {
					for (int y = 0; y <= Chunk.MAX_CHUNK_COORD_Y; y++) {
						for (int z = -16; z <= Chunk.MAX_CHUNK_COORD_XZ; z++) {
							try {
								if(chunk.getBlockTypeAtChunkCoord(x, y, z) != AirBlock.class) {

									Vector3 a, b, c, d, e, f, g, h;

									// add front face (quad, not triangles)
									mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z - 0.5f + absZ));
									mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z + 0.5f + absZ));										
									mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z + 0.5f + absZ));										
									mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z - 0.5f + absZ));


									// check if the neighbour block is air or the edge of the chunk has been reached
									if(y < 0 || chunk.getBlockTypeAtChunkCoord(x, y + 1, z) == AirBlock.class) {

										// front face
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z - 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z + 0.5f + absZ));										
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z + 0.5f + absZ));										
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z - 0.5f + absZ));										
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z + 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z - 0.5f + absZ));				
									}

									if(y <= 0 || chunk.getBlockTypeAtChunkCoord(x, y - 1, z) == AirBlock.class) {

										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y - 0.5f, z - 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y - 0.5f, z + 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y - 0.5f, z + 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y - 0.5f, z - 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y - 0.5f, z - 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y - 0.5f, z + 0.5f + absZ));

										// normal: 0, -1, 0
										//										
										//uv
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										
										//										vertices.add(0.2f);
										//										vertices.add(0.2f);
										//										
										//										vertices.add(0.0f);
										//										vertices.add(0.2f);
										//										
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										
										//										vertices.add(0.2f);
										//										vertices.add(0.0f);
										//										
										//										vertices.add(0.2f);
										//										vertices.add(0.2f);
									}
									if(z <= -16 || chunk.getBlockTypeAtChunkCoord(x, y, z - 1) ==  AirBlock.class) {

										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y - 0.5f, z - 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z - 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z - 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y - 0.5f, z - 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z - 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y - 0.5f, z - 0.5f + absZ));

										// normal: 0, 0, -1

										//										vertices.add(0.4f);	vertices.add(0.4f);										
										//										vertices.add(0.4f);	vertices.add(0.6f);										
										//										vertices.add(0.6f);	vertices.add(0.6f);										
										//										vertices.add(0.4f);	vertices.add(0.4f);										
										//										vertices.add(0.6f);	vertices.add(0.6f);										
										//										vertices.add(0.6f);	vertices.add(0.4f);
									}									
									if(z >= 16 || chunk.getBlockTypeAtChunkCoord(x, y, z + 1) == AirBlock.class) {

										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y - 0.5f, z + 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y - 0.5f, z + 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z + 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y - 0.5f, z + 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z + 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z + 0.5f + absZ));

										// normal 0, 0, 1

										//										vertices.add(0.4f);	vertices.add(0.4f);										
										//										vertices.add(0.4f);	vertices.add(0.6f);										
										//										vertices.add(0.6f);	vertices.add(0.6f);										
										//										vertices.add(0.4f); vertices.add(0.4f);
										//										vertices.add(0.6f);	vertices.add(0.6f);										
										//										vertices.add(0.6f);	vertices.add(0.4f);
									}									
									if(x <= -16 || chunk.getBlockTypeAtChunkCoord(x - 1, y, z) == AirBlock.class) {

										mBuilder.addVertex(new Vector3());
										mBuilder.addVertex(new Vector3());
										mBuilder.addVertex(new Vector3());
										mBuilder.addVertex(new Vector3());
										mBuilder.addVertex(new Vector3());
										mBuilder.addVertex(new Vector3());

										// normal -1, 0, 0

										//										vertices.add(x - 0.5f + absX);
										//										vertices.add(y - 0.5f);
										//										vertices.add(z - 0.5f + absZ);
										//										vertices.add(-1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.6f);
										//										vertices.add(0.6f);
										//										
										//										vertices.add(x - 0.5f + absX);
										//										vertices.add(y - 0.5f);
										//										vertices.add(z + 0.5f + absZ);
										//										vertices.add(-1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.6f);
										//										vertices.add(0.8f);
										//										
										//										vertices.add(x - 0.5f + absX);
										//										vertices.add(y + 0.5f);
										//										vertices.add(z + 0.5f + absZ);
										//										vertices.add(-1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.8f);
										//										vertices.add(0.8f);
										//										
										//										vertices.add(x - 0.5f + absX);
										//										vertices.add(y - 0.5f);
										//										vertices.add(z - 0.5f + absZ);
										//										vertices.add(-1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.6f);
										//										vertices.add(0.6f);
										//										
										//										vertices.add(x - 0.5f + absX);
										//										vertices.add(y + 0.5f);
										//										vertices.add(z + 0.5f + absZ);
										//										vertices.add(-1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.8f);
										//										vertices.add(0.8f);
										//										
										//										vertices.add(x - 0.5f + absX);
										//										vertices.add(y + 0.5f);
										//										vertices.add(z - 0.5f + absZ);
										//										vertices.add(-1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.8f);
										//										vertices.add(0.6f);
									}
									if(x >= 16 || chunk.getBlockTypeAtChunkCoord(x + 1, y, z) == AirBlock.class) {

										mBuilder.addVertex(new Vector3());
										mBuilder.addVertex(new Vector3());
										mBuilder.addVertex(new Vector3());
										mBuilder.addVertex(new Vector3());
										mBuilder.addVertex(new Vector3());
										mBuilder.addVertex(new Vector3());

										// normal 1, 0, 0

										//										vertices.add(x + 0.5f + absX);
										//										vertices.add(y - 0.5f);
										//										vertices.add(z - 0.5f + absZ);
										//										vertices.add(+1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.8f);
										//										vertices.add(0.8f);
										//										
										//										vertices.add(x + 0.5f + absX);
										//										vertices.add(y + 0.5f);
										//										vertices.add(z - 0.5f + absZ);
										//										vertices.add(1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(1.0f);
										//										vertices.add(0.8f);
										//										
										//										vertices.add(x + 0.5f + absX);
										//										vertices.add(y + 0.5f);
										//										vertices.add(z + 0.5f + absZ);
										//										vertices.add(1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(1.0f);
										//										vertices.add(1.0f);
										//										
										//										vertices.add(x + 0.5f + absX);
										//										vertices.add(y - 0.5f);
										//										vertices.add(z - 0.5f + absZ);
										//										vertices.add(1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.8f);
										//										vertices.add(0.8f);
										//										
										//										vertices.add(x + 0.5f + absX);
										//										vertices.add(y + 0.5f);
										//										vertices.add(z + 0.5f + absZ);
										//										vertices.add(1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(1.0f);
										//										vertices.add(1.0f);
										//										
										//										vertices.add(x + 0.5f + absX);
										//										vertices.add(y - 0.5f);
										//										vertices.add(z + 0.5f + absZ);
										//										vertices.add(1.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.0f);
										//										vertices.add(0.8f);
										//										vertices.add(1.0f);
									}


								}
							} catch (OutOfChunkBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				}
			}

			/*
			Mesh mesh = new Mesh(true, i, 0, 
					new VertexAttribute(Usage.Position, 3, "a_position"));
			 */


		} catch (ChunkNotExistingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MiniMineMesh mesh = mBuilder.create();

		return mesh;
	}

	public Mesh generateBox() {

		MiniMineQuadVtxBuilder mBuilder = new MiniMineQuadVtxBuilder();
		mBuilder.addVertex(vA);
		mBuilder.addVertex(vB);
		mBuilder.addVertex(vC);
		mBuilder.addVertex(vD);
		mBuilder.addVertex(vE);
		mBuilder.addVertex(vF);
		mBuilder.addVertex(vG);
		mBuilder.addVertex(vH);
		mBuilder.addQuad(0, 1, 2, 3);
		mBuilder.addQuad(0, 1, 2, 3);
		mBuilder.addQuad(0, 1, 2, 3);
		mBuilder.addQuad(0, 1, 2, 3);
		mBuilder.addQuad(0, 1, 2, 3);
		mBuilder.addQuad(0, 1, 2, 3);

		//		Mesh mesh = new Mesh();

		return null;
	}

	/**
	 * 
	 * @return
	 */
	public Model generateTestModel() {

		// vertices of a block
		Vector3 a, b, c, d, e, f, g, h;
		a = vA;
		b = vB;
		c = vC;
		d = vD;
		e = vE;
		f = vF;
		g = vG;
		h = vH;
		
		Model model = new Model();

		ModelBuilder modelBuilder = new ModelBuilder();
		int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal 
				| VertexAttributes.Usage.ColorPacked;

		modelBuilder.begin();
		MeshPartBuilder mpb = modelBuilder.part("box", GL20.GL_TRIANGLES, attr, new Material());
		mpb.setColor(Color.BLUE);
		mpb.rect(a, b, c, d, nFront);
		mpb.setColor(Color.GREEN);
		mpb.rect(f, e, h, g, nBack);
		mpb.setColor(Color.RED);
		mpb.rect(e, f, b, a, nBottom); 
		mpb.setColor(Color.ORANGE);
		mpb.rect(d, c, g, h, nTop);
		mpb.setColor(Color.YELLOW);
		mpb.rect(e, a, d, h, nLeft);
		mpb.setColor(Color.PINK);
		mpb.rect(b, f, g, c, nRight); 
		return modelBuilder.end();
	}
	
	/**
	 * Build a single cub with 6 faces and 2 triangles per face.
	 */
	public Mesh buildCube() {
		// vertices of a block
		Vector3 a, b, c, d, e, f, g, h;
		//		a = new Vector3(-2f, -2f, -2f);
		//		b = new Vector3(-2f, 2f, -2f);
		//		c = new Vector3(2f, 2f, -2f);
		//		d = new Vector3(2f, -2f, -2f);
		//		e = new Vector3(-2f, -2f, 2f);
		//		f = new Vector3(-2f, 2f, 2f);
		//		g = new Vector3(2f, 2f, 2f);
		//		h = new Vector3(2f, -2f, 2f);
		a = vA;
		b = vB;
		c = vC;
		d = vD;
		e = vE;
		f = vF;
		g = vG;
		h = vH;

		MiniMineAbstractTriangleMeshBuilder builder = new MiniMineTriangleVtxBuilder();
		builder.color = Color.PINK;

		int index = 0;

		// front face
		builder.addVertex(a);
		builder.addVertex(b);
		builder.addVertex(c);
		builder.addVertex(d);
		builder.addTriangle(index, index+1, index+2);
		builder.addTriangle(index, index+2, index+3);
		// back face
		builder.addVertex(f);
		builder.addVertex(e);
		builder.addVertex(h);
		builder.addVertex(g);
		index += 4;
		builder.addTriangle(index, index+1, index+2);
		builder.addTriangle(index, index+2, index+3);
		// top face
		builder.addVertex(d);
		builder.addVertex(c);
		builder.addVertex(g);
		builder.addVertex(h);
		index += 4;
		builder.addTriangle(index, index+1, index+2);
		builder.addTriangle(index, index+2, index+3);
		// bottom face
		builder.addVertex(e);
		builder.addVertex(f);
		builder.addVertex(b);
		builder.addVertex(a);
		index += 4;
		builder.addTriangle(index, index+1, index+2);
		builder.addTriangle(index, index+2, index+3);
		// right face
		builder.addVertex(b);
		builder.addVertex(f);
		builder.addVertex(g);
		builder.addVertex(c);
		index += 4;
		builder.addTriangle(index, index+1, index+2);
		builder.addTriangle(index, index+2, index+3);
		// left face
		builder.addVertex(e);
		builder.addVertex(a);
		builder.addVertex(d);
		builder.addVertex(h);
		index += 4;
		builder.addTriangle(index, index+1, index+2);
		builder.addTriangle(index, index+2, index+3);
		
		return builder.createMesh();
	}
	

	// http://stackoverflow.com/questions/27452192/libgdx-mapping-individual-textures-to-each-face-of-a-box-using-modelbuilder-cr

	//  model = modelBuilder.createSphere(2f, 2f, 2f, 20, 20, 
	//  model = modelBuilder.createBox(5f, 5f, 5f, 
	//    new Material(), Usage.Position | Usage.Normal | Usage.TextureCoordinates);

	//  modelBuilder.begin();
	//  modelBuilder.part(id, mesh, primitiveType, material);
	//  model = modelBuilder.end();
	
	
	

}