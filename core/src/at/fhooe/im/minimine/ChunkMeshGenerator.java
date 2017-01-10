package at.fhooe.im.minimine;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

import at.fhooe.im.minimine.exception.OutOfChunkBoundsException;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.block.AirBlock;
import at.fhooe.im.minimine.world.block.DirtBlock;
import at.fhooe.im.minimine.world.block.StoneBlock;
import at.fhooe.im.minimine.world.block.TNTBlock;
import at.fhooe.im.minimine.world.block.WoodBlock;

public class ChunkMeshGenerator {
	public Mesh generateChunk(Chunk _chunk, int xPos, int zPos) {
		LinkedList<Float> vertices = new LinkedList<Float>();
		int absX = xPos * 33;
		int absZ = zPos * 33;
		for(int x = -16; x <= Chunk.MAX_CHUNK_COORD_XZ; x++) {
			for(int y = 0; y <= Chunk.MAX_CHUNK_COORD_Y; y++) {
				for(int z = -16; z <= Chunk.MAX_CHUNK_COORD_XZ; z++) {
					try {
						Class<?> type = _chunk.getBlockTypeAtChunkCoord(x, y, z);
						if(type != AirBlock.class) {
							float startY = 0.0f;
							float endY = 0.25f;
							if(type == DirtBlock.class) {
								startY = 0.0f;
								endY = 0.25f;
							} else if(type == StoneBlock.class) {
								startY = 0.25f;
								endY = 0.5f;
							} else if(type == TNTBlock.class) {
								startY = 0.5f;
								endY = 0.75f;
							} else if(type == WoodBlock.class) {
								startY = 0.75f;
								endY = 1.0f;
							}
							//check above
							if(y < 0 || _chunk.getBlockTypeAtChunkCoord(x, y + 1, z) == AirBlock.class) {
								//draw the top side
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.1666667f);
								vertices.add(startY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.1666667f);
								vertices.add(endY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.333333f);
								vertices.add(endY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.166667f);
								vertices.add(startY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.33333f);
								vertices.add(endY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.33333f);
								vertices.add(startY);
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
								vertices.add(startY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.166f);
								vertices.add(endY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(endY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(startY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.166f);
								vertices.add(startY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.166f);
								vertices.add(endY);
							}
							//check front
							if(z <= -16 || _chunk.getBlockTypeAtChunkCoord(x, y, z - 1) ==  AirBlock.class) {
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.5f);
								vertices.add(endY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.5f);
								vertices.add(startY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.3333333f);
								vertices.add(startY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.5f);
								vertices.add(endY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.3333333f);
								vertices.add(startY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(-1.0f);
								vertices.add(0.333333f);
								vertices.add(endY);
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
								vertices.add(endY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.666f);
								vertices.add(endY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.666f);
								vertices.add(startY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.5f);
								vertices.add(endY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.666f);
								vertices.add(startY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(0.5f);
								vertices.add(startY);
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
								vertices.add(endY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.8333f);
								vertices.add(endY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.8333f);
								vertices.add(startY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.666f);
								vertices.add(endY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.8333f);
								vertices.add(startY);
								
								vertices.add(x - 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(-1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.666f);
								vertices.add(startY);
							}
							
							if(x >= 16 || _chunk.getBlockTypeAtChunkCoord(x + 1, y, z) == AirBlock.class) {
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(+1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(endY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(startY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.833333f);
								vertices.add(startY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z - 0.5f + absZ);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(1.0f);
								vertices.add(endY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y + 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.833333f);
								vertices.add(startY);
								
								vertices.add(x + 0.5f + absX);
								vertices.add(y - 0.5f);
								vertices.add(z + 0.5f + absZ);
								vertices.add(1.0f);
								vertices.add(0.0f);
								vertices.add(0.0f);
								vertices.add(0.833333f);
								vertices.add(endY);
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
