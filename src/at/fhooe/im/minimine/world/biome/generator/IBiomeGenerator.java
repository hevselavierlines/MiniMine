package at.fhooe.im.minimine.world.biome.generator;

import at.fhooe.im.minimine.world.Chunk;

public interface IBiomeGenerator {

	/**
	 * Generates the chunk
	 * @return Chunk
	 */
	public Chunk generateChunk();
	
}
