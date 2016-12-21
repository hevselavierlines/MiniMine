package at.fhooe.im.minimine.world.biome.generator;

import java.util.Random;

import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.biome.Biomes;
import at.fhooe.im.minimine.world.block.DirtBlock;

public class UphillBiomeGenerator extends AbstractBiomeGenerator {
	
	@Override
	public Chunk generateChunk(World w, int m, int n) {
		Chunk newChunk = new Chunk(m, n, Biomes.UPHILL);
		
		Chunk north = this.getNorthNeighbour(w, m, n);
		Chunk east = this.getEastNeighbour(w, m, n);
		Chunk south = this.getSouthNeighbour(w, m, n);
		Chunk west = this.getWestNeighbour(w, m, n);
		int amountExistingNeighbours = getAmountExistingNeighbours(north, east, south, west);
		
		switch(amountExistingNeighbours) {
			case 1: {
				Chunk neighbour = null;
				int neighbourPos = -1;
				
				if (north != null) {
					neighbour = north;
					neighbourPos = AbstractBiomeGenerator.NORTH_NEIGHBOUR;
				}
				if (east != null) {
					neighbour = east;
					neighbourPos = AbstractBiomeGenerator.EAST_NEIGHBOUR;
				}
				if (south != null) {
					neighbour = south;
					neighbourPos = AbstractBiomeGenerator.SOUTH_NEIGHBOUR;
				}
				if (west != null) {
					neighbour = west;
					neighbourPos = AbstractBiomeGenerator.WEST_NEIGHBOUR;
				}
				
				newChunk = generateWithOneNeighbour(newChunk, neighbour, neighbourPos);
				break;
			}
		}
		
		return newChunk;
	}
	
	private Chunk generateWithOneNeighbour(Chunk newChunk, Chunk neighbour, int neighbourPos) {
		int startHeight = 0;
		switch (neighbourPos) {
			case AbstractBiomeGenerator.NORTH_NEIGHBOUR: {
				
				break;
			}
		}
	}
	
}
