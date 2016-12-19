
public abstract class AbstractBiomeGenerator {

	public final static int NORTH_NEIGHBOUR = 1;
	public final static int EAST_NEIGHBOUR = 2;
	public final static int SOUTH_NEIGHBOUR = 3;
	public final static int WEST_NEIGHBOUR = 4;
	
	public abstract Chunk generateChunk(World w, int m, int n);
	
	protected int getAmountExistingNeighbours(Chunk north, Chunk east, Chunk south, Chunk west) {
		int amount = 0;
		if(north != null) amount++;
		if(east != null) amount++;
		if(south != null) amount++;
		if(west != null) amount++;
		return amount;
	}
	
	protected Chunk getNorthNeighbour(World w, int m, int n) {
		return this.getChunk(w, m, n + 1);
	}
	
	protected Chunk getEastNeighbour(World w, int m, int n) {
		return this.getChunk(w, m + 1, n);
	}
	
	protected Chunk getSouthNeighbour(World w, int m, int n) {
		return this.getChunk(w, m, n - 1);
	}
	
	protected Chunk getWestNeighbour(World w, int m, int n) {
		return this.getChunk(w, m - 1, n);
	}
	
	protected Chunk getChunk(World w, int m, int n) {
		Chunk c = null; 
		try {
			c = w.getChunk(m, n);
		} catch (ChunkNotExistingException e) {
			return null;
		}
		return c;
	}
	
}
