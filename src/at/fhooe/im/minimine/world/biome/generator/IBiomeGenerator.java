package at.fhooe.im.minimine.world.biome.generator;

import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;

public interface IBiomeGenerator {

	/**
	 * Generates the chunk
	 * @param w World
	 * @param m x-pos of Chunk
	 * @param n y-pos of Chunk
	 * @return Chunk
	 */
	public Chunk generateChunk(World w, int m, int n);
	
}
