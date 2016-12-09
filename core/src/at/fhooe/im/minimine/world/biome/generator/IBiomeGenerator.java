package at.fhooe.im.minimine.world.biome.generator;

import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;

public interface IBiomeGenerator {

	/**
	 * Generates the chunk
	 * @return Chunk
	 */
	public Chunk generateChunk(World w, int m, int n);
	
}
