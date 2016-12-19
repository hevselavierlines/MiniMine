package at.fhooe.im.minimine.meshbuilder;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
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
 */
public class MiniMineMeshGenerator {

	/**
	 * 
	 */
	public MiniMineMeshGenerator() {

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
									
									// add first face
									mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z - 0.5f + absZ));
									mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z + 0.5f + absZ));										
									mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z + 0.5f + absZ));										
									mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z - 0.5f + absZ));
									
									
									if(y < 0 || chunk.getBlockTypeAtChunkCoord(x, y + 1, z) == AirBlock.class) {
										
										
										
										// manuel
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z - 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z + 0.5f + absZ));										
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z + 0.5f + absZ));										
										mBuilder.addVertex(new Vector3(x - 0.5f + absX, y + 0.5f, z - 0.5f + absZ));										
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z + 0.5f + absZ));
										mBuilder.addVertex(new Vector3(x + 0.5f + absX, y + 0.5f, z - 0.5f + absZ));				
									}
									
									if(y <= 0 || chunk.getBlockTypeAtChunkCoord(x, y - 1, z) == AirBlock.class) {
//										vertices.add(x - 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z - 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										
//										vertices.add(x + 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z + 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.0f);
//										vertices.add(0.2f);
//										vertices.add(0.2f);
//										
//										vertices.add(x - 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z + 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(0.2f);
//										
//										vertices.add(x - 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z - 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										
//										vertices.add(x + 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z - 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.0f);
//										vertices.add(0.2f);
//										vertices.add(0.0f);
//										
//										vertices.add(x + 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z + 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.0f);
//										vertices.add(0.2f);
//										vertices.add(0.2f);
//									}
//									if(z <= -16 || chunk.getBlockTypeAtChunkCoord(x, y, z - 1) ==  AirBlock.class) {
//										vertices.add(x - 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z - 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.4f);
//										vertices.add(0.4f);
//										
//										vertices.add(x - 0.5f + absX);
//										vertices.add(y + 0.5f);
//										vertices.add(z - 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.4f);
//										vertices.add(0.6f);
//										
//										vertices.add(x + 0.5f + absX);
//										vertices.add(y + 0.5f);
//										vertices.add(z - 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.6f);
//										vertices.add(0.6f);
//										
//										vertices.add(x - 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z - 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.4f);
//										vertices.add(0.4f);
//										
//										vertices.add(x + 0.5f + absX);
//										vertices.add(y + 0.5f);
//										vertices.add(z - 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.6f);
//										vertices.add(0.6f);
//										
//										vertices.add(x + 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z - 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(-1.0f);
//										vertices.add(0.6f);
//										vertices.add(0.4f);
//									}
//									
//									if(z >= 16 || chunk.getBlockTypeAtChunkCoord(x, y, z + 1) == AirBlock.class) {
//										vertices.add(x - 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z + 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(1.0f);
//										vertices.add(0.4f);
//										vertices.add(0.4f);
//										
//										vertices.add(x + 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z + 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(1.0f);
//										vertices.add(0.4f);
//										vertices.add(0.6f);
//										
//										vertices.add(x + 0.5f + absX);
//										vertices.add(y + 0.5f);
//										vertices.add(z + 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(1.0f);
//										vertices.add(0.6f);
//										vertices.add(0.6f);
//										
//										vertices.add(x - 0.5f + absX);
//										vertices.add(y - 0.5f);
//										vertices.add(z + 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(1.0f);
//										vertices.add(0.4f);
//										vertices.add(0.4f);
//										
//										vertices.add(x + 0.5f + absX);
//										vertices.add(y + 0.5f);
//										vertices.add(z + 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(1.0f);
//										vertices.add(0.6f);
//										vertices.add(0.6f);
//										
//										vertices.add(x - 0.5f + absX);
//										vertices.add(y + 0.5f);
//										vertices.add(z + 0.5f + absZ);
//										vertices.add(0.0f);
//										vertices.add(0.0f);
//										vertices.add(1.0f);
//										vertices.add(0.6f);
//										vertices.add(0.4f);
//									}
//									
//									if(x <= -16 || chunk.getBlockTypeAtChunkCoord(x - 1, y, z) == AirBlock.class) {
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
//									}
//									
//									if(x >= 16 || chunk.getBlockTypeAtChunkCoord(x + 1, y, z) == AirBlock.class) {
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
	
	/*
	 * TODO MiniMineQuadVtxNrmUvColBuilder für Ambient Occlusion 
	 * 
	 */

}
