package at.fhooe.im.minimine.world.biome.generator;

import java.util.Random;

import at.fhooe.im.minimine.exception.ChunkNotExistingException;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.block.DirtBlock;

public class FlatlandBiomeGenerator implements IBiomeGenerator {

	@Override
	public Chunk generateChunk(World w, int m, int n) {
		Chunk newChunk = new Chunk(m, n);
		
		Chunk north = this.getNorthNeighbour(w, m, n);
		Chunk east = this.getEastNeighbour(w, m, n);
		Chunk south = this.getSouthNeighbour(w, m, n);
		Chunk west = this.getWestNeighbour(w, m, n);
		
		if(north == null && east == null && south == null && west == null) {
			// first generated Chunk (spawn point of player)
			Random random = new Random();
			int height = random.nextInt(100) + 50;
			newChunk.fillChunkUp(DirtBlock.class, height);
		}
		// TODO Else
		// TODO random ups and downs
		return null;
		
	}
	
	private Chunk getNorthNeighbour(World w, int m, int n) {
		return this.getChunk(w, m, n + 1);
	}
	
	private Chunk getEastNeighbour(World w, int m, int n) {
		return this.getChunk(w, m + 1, n);
	}
	
	private Chunk getSouthNeighbour(World w, int m, int n) {
		return this.getChunk(w, m, n - 1);
	}
	
	private Chunk getWestNeighbour(World w, int m, int n) {
		return this.getChunk(w, m - 1, n);
	}
	
	private Chunk getChunk(World w, int m, int n) {
		Chunk c = null; 
		try {
			c = w.getChunk(m, n);
		} catch (ChunkNotExistingException e) {
			return null;
		}
		return c;
	}
	
}
