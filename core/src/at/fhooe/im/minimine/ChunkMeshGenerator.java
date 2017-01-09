package at.fhooe.im.minimine;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

import at.fhooe.im.minimine.exception.OutOfChunkBoundsException;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.block.AirBlock;

public class ChunkMeshGenerator {
	public Mesh generateChunk(Chunk _chunk, int xPos, int zPos) {
		LinkedList<Float> vertices = new LinkedList<Float>();
		int absX = xPos * 33;
		int absZ = zPos * 33;
		for(int x = -16; x <= Chunk.MAX_CHUNK_COORD_XZ; x++) {
			for(int y = 0; y <= Chunk.MAX_CHUNK_COORD_Y; y++) {
				for(int z = -16; z <= Chunk.MAX_CHUNK_COORD_XZ; z++) {
					try {
						if(_chunk.getBlockTypeAtChunkCoord(x, y, z) != AirBlock.class) {
							//check above
							if(y < 0 || _chunk.getBlockTypeAtChunkCoord(x, y + 1, z) == AirBlock.class) {
								//draw the top side
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.166f);
								vertices.add(0.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.166f);
								vertices.add(1.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.333f);
								vertices.add(1.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.166f);
								vertices.add(0.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.333f);
								vertices.add(1.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.333f);
								vertices.add(0.0f);
							}
							//check below
							if(y <= 0 || _chunk.getBlockTypeAtChunkCoord(x, y - 1, z) == AirBlock.class) {
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.166f);
								vertices.add(1.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.166f);
								vertices.add(0.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.166f);
								vertices.add(1.0f);
							}
							//check front
							if(z <= -16 || _chunk.getBlockTypeAtChunkCoord(x, y, z - 1) ==  AirBlock.class) {
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.333f);
								vertices.add(1.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.333f);
								vertices.add(0.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.5f);
								vertices.add(0.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.333f);
								vertices.add(1.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.5f);
								vertices.add(0.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.5f);
								vertices.add(1.0f);
							}
							//check back
							if(z >= 16 || _chunk.getBlockTypeAtChunkCoord(x, y, z + 1) == AirBlock.class) {
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.5f);
								vertices.add(1.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.666f);
								vertices.add(1.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.666f);
								vertices.add(0.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.5f);
								vertices.add(1.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.666f);
								vertices.add(0.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.5f);
								vertices.add(0.0f);
							}
							//check left
							if(x <= -16 || _chunk.getBlockTypeAtChunkCoord(x - 1, y, z) == AirBlock.class) {
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.666f);
								vertices.add(1.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.8333f);
								vertices.add(1.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.8333f);
								vertices.add(0.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.666f);
								vertices.add(1.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.8333f);
								vertices.add(0.0f);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.666f);
								vertices.add(0.0f);
							}
							
							if(x >= 16 || _chunk.getBlockTypeAtChunkCoord(x + 1, y, z) == AirBlock.class) {
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(+1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.8333f);
								vertices.add(1.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.8333f);
								vertices.add(0.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.8333f);
								vertices.add(1.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(1.0f);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
							}
						}
						
					} catch (OutOfChunkBoundsException e) {
						e.printStackTrace();
					}
				}
			}
		}
		float[] meshInfo = new float[vertices.size()];
		int i = 0;
		for(Float meshData : vertices) {
			meshInfo[i++] = meshData;
		}
		vertices.clear();
		Mesh mesh = new Mesh(true, i, 0, new VertexAttribute(Usage.Position, 3, "a_position"), new VertexAttribute(Usage.Normal,
				3, "a_normal"), new VertexAttribute(Usage.TextureCoordinates, 2, "a_texcoords"));
		mesh.setVertices(meshInfo);
		return mesh;
	}
}
