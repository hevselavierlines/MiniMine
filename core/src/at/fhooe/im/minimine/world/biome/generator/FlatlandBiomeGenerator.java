package at.fhooe.im.minimine.world.biome.generator;

import java.util.Random;

import at.fhooe.im.minimine.exception.ChunkNotExistingException;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.block.DirtBlock;

public class FlatlandBiomeGenerator extends AbstractBiomeGenerator {

	public final static int NORTH_NEIGHBOUR = 1;
	public final static int EAST_NEIGHBOUR = 2;
	public final static int SOUTH_NEIGHBOUR = 3;
	public final static int WEST_NEIGHBOUR = 4;
	
	@Override
	public Chunk generateChunk(World w, int m, int n) {
		Chunk newChunk = new Chunk(m, n);
		
		Chunk north = this.getNorthNeighbour(w, m, n);
		Chunk east = this.getEastNeighbour(w, m, n);
		Chunk south = this.getSouthNeighbour(w, m, n);
		Chunk west = this.getWestNeighbour(w, m, n);
		int amountExistingNeighbours = getAmountExistingNeighbours(north, east, south, west);
		
		int height = 0;
		
		int dividor = Chunk.CHUNK_SIZE_XZ * amountExistingNeighbours;
		if (dividor == 0) {
			Random random = new Random();
			height = random.nextInt(100) + 50;
		} else {
			int summedHeight = 0;
			if (north != null) {
				for(int x = -16; x <= 16; x++) {
					summedHeight += north.getMaximumHeightAtChunkCoord(x, -16);
				}
			}
			if (east != null) {
				for(int z = -16; z <= 16; z++) {
					summedHeight += east.getMaximumHeightAtChunkCoord(-16, z);
				}
			}
			if (south != null) {
				for(int x = -16; x <= 16; x++) {
					summedHeight += south.getMaximumHeightAtChunkCoord(x, 16);
				}
			}
			if (west != null) {
				for(int z = -16; z <= 16; z++) {
					summedHeight += west.getMaximumHeightAtChunkCoord(16, z);
				}
			}
			height = summedHeight / dividor;
		}
		
		newChunk.fillChunkUp(DirtBlock.class, height);

		return newChunk;
		
	}
	
}
