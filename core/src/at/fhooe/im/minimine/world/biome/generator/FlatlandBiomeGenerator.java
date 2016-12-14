package at.fhooe.im.minimine.world.biome.generator;

import java.util.Random;

import at.fhooe.im.minimine.exception.ChunkNotExistingException;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.block.DirtBlock;

public class FlatlandBiomeGenerator implements IBiomeGenerator {

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
		switch(amountExistingNeighbours) {
			case 0: {
				// first generated Chunk (spawn point of player)
				Random random = new Random();
				height = random.nextInt(100) + 50;
				break;
			}
			case 1: {
				Chunk neighbour = null;
				int neighbourPosition = 0;
				if(north != null) {
					neighbourPosition = NORTH_NEIGHBOUR;
					neighbour = north;
				}
				if(east != null) {
					neighbourPosition = EAST_NEIGHBOUR;
					neighbour = east;
				}
				if(south != null) {
					neighbourPosition = SOUTH_NEIGHBOUR;
					neighbour = south;
				}
				if(west != null) {
					neighbourPosition = WEST_NEIGHBOUR;
					neighbour = west;
				}
				
				// TODO übergangsverbesserung über mehrere zeilen NACH der chunk generierung
				// average height of adjacent side neighbour
				int height = 0;
				switch(neighbourPosition) {
					case NORTH_NEIGHBOUR: {
						for(int x = -16; x <= 16; x++) {
							height += neighbour.getMaximumHeightAtChunkCoord(x, -16);
						}
						break;
					}
					case EAST_NEIGHBOUR: {
						for(int z = -16; z <= 16; z++) {
							height += neighbour.getMaximumHeightAtChunkCoord(-16, z);
						}
						break;
					}
					case SOUTH_NEIGHBOUR: {
						for(int x = -16; x <= 16; x++) {
							height += neighbour.getMaximumHeightAtChunkCoord(x, 16);
						}
						break;
					}
					case WEST_NEIGHBOUR: {
						for(int z = -16; z <= 16; z++) {
							height += neighbour.getMaximumHeightAtChunkCoord(16, z);
						}
						break;
					}
				}
				height /= 33;
				
				break;
			}
		}
		
		newChunk.fillChunkUp(DirtBlock.class, height);

		// TODO Else
		// TODO random ups and downs
		return null;
		
	}
	
	private int getAmountExistingNeighbours(Chunk north, Chunk east, Chunk south, Chunk west) {
		int amount = 0;
		if(north != null) amount++;
		if(east != null) amount++;
		if(south != null) amount++;
		if(west != null) amount++;
		return amount;
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
