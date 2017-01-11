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
			default: {
				System.out.println("Not one neighbour, but " + amountExistingNeighbours);
				break;
			}
		}
		
		return newChunk;
	}
	
	private Chunk generateWithOneNeighbour(Chunk newChunk, Chunk neighbour, int neighbourPos) {
		System.out.println("Generating UphillBiome");
		int oldHeight = -1;
		switch (neighbourPos) {
			case AbstractBiomeGenerator.NORTH_NEIGHBOUR: {
				for (int z = 16; z >= -16; z--) {
					// get old height
					int height;
					if (z == 16) {
						height = neighbour.getMaximumHeightAtChunkCoord(0, -16);
						
					} else {
						height = oldHeight;
					}
					// generate new height
					Random rand = new Random();
					int caseH = rand.nextInt(5);
					switch(caseH) {
						case 0: case 1: break;
						case 2: case 3: height++; break;
						case 4: height += 2; break;
						default: break;
					}
					// apply new height
					for (int x = 16; x >= -16; x--) {
						newChunk.fillChunkArrayCoordUp(DirtBlock.class, 32 - (x + 16), height, 32 - (z + 16));
					}
					oldHeight = height;
				}
				break;
			}
			case AbstractBiomeGenerator.EAST_NEIGHBOUR: {
				for (int x = -16; x <= 16; x++) {
					// get old height
					int height;
					if (x == -16) {
						height = neighbour.getMaximumHeightAtChunkCoord(16, 0);
						
					} else {
						height = oldHeight;
					}
					// generate new height
					Random rand = new Random();
					int caseH = rand.nextInt(5);
					switch(caseH) {
					case 0: case 1: break;
					case 2: case 3: height++; break;
					case 4: height += 2; break;
					default: break;
				}
				// apply new height
				for (int z = 16; z >= -16; z--) {
					newChunk.fillChunkArrayCoordUp(DirtBlock.class, 32 - (x + 16), height, 32 - (z + 16));
				}
				oldHeight = height;
				}
				break;
			}
			case AbstractBiomeGenerator.SOUTH_NEIGHBOUR: {
				for (int z = -16; z <= 16; z++) {
					// get old height
					int height;
					if (z == -16) {
						height = neighbour.getMaximumHeightAtChunkCoord(0, 16);
						
					} else {
						height = oldHeight;
					}
					// generate new height
					Random rand = new Random();
					int caseH = rand.nextInt(5);
					switch(caseH) {
						case 0: case 1: break;
						case 2: case 3: height++; break;
						case 4: height += 2; break;
						default: break;
					}
					// apply new height
					for (int x = 16; x >= -16; x--) {
						newChunk.fillChunkArrayCoordUp(DirtBlock.class, 32 - (x + 16), height, 32 - (z + 16));
					}
					oldHeight = height;
				}
				break;
			}
			case AbstractBiomeGenerator.WEST_NEIGHBOUR: {
				for (int x = 16; x >= -16; x--) {
					// get old height
					int height;
					if (x == 16) {
						height = neighbour.getMaximumHeightAtChunkCoord(-16, 0);
					} else {
						height = oldHeight;
					}
					// generate new height
					Random rand = new Random();
					int caseH = rand.nextInt(5);
					switch(caseH) {
						case 0: case 1: break;
						case 2: case 3: height++; break;
						case 4: height += 2; break;
						default: break;
					}
					// apply new height
					for (int z = 16; z >= -16; z--) {
						newChunk.fillChunkArrayCoordUp(DirtBlock.class, 32 - (x + 16), height, 32 - (z + 16));
					}
					oldHeight = height;
				}
				break;
			}
		}
		return newChunk;
	}
	
}
