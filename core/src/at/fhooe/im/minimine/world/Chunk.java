package at.fhooe.im.minimine.world;

import java.awt.Point;

import at.fhooe.im.minimine.exception.OutOfChunkBoundsException;
import at.fhooe.im.minimine.world.block.AbstractBlock;
import at.fhooe.im.minimine.world.block.AirBlock;

public class Chunk {

	public static final int CHUNK_SIZE_XZ = 33;
	public static final int MAX_CHUNK_COORD_XZ = 16;
	public static final int MAX_CHUNK_COORD_Y = 255;
	
	private Point pos;
	private AbstractBlock[][][] blocks;
	private int biomeId;
	
	/*
	 * The position of the chunk is saved as grid position
	 * 0|0 is the center chunk
	 * The chunk to the upper left is the -1|1 chunk
	 * 
	 * 
	 * The block-array in x|z is build up like a cartesian coordinate system
	 * The y-coordinate works linear (idx 0 = bottom layer, idx 255 = top layer)
	 * There are several converter-methods at the bottom of the class ( Chunk.convert...To...(...) )
	 * IDX	   0           1       ..      1 5         1 6         1 7      ..      3 1         3 2    ---> x
	 *  0  -16 |  16   -15 |  16   ..    -1 |  16     0 |  16     1 |  16   ..    15 |  16    16 |  16
	 *  1  -16 |  15   -15 |  15   ..    -1 |  15     0 |  15     1 |  15   ..    15 |  15    16 |  15
	 * ..     ...         ...      ..      ...         ...         ...      ..      ...         ...
	 * 15  -16 |   1   -15 |   1   ..    -1 |   1     0 |   1     1 |   1   ..    15 |   1    16 |   1
	 * 16  -16 |   0   -15 |   0   ..    -1 |   0     0 |   0     1 |   0   ..    15 |   0    16 |   0
	 * 17  -16 |  -1   -15 |  -1   ..    -1 |  -1     0 |  -1     1 |  -1   ..    15 |  -1    16 |  -1
	 * ..     ...         ...      ..      ...         ...         ...      ..      ...         ...
	 * 31  -16 | -15   -15 | -15   ..    -1 | -15     0 | -15     1 | -15   ..    15 | -15    16 | -15
	 * 32  -16 | -16   -15 | -16   ..    -1 | -16     0 | -16     1 | -16   ..    15 | -16    16 | -16
	 * 
	 * |
	 * |
	 * |
	 * v
	 * 
	 * z
	 */
	
	/**
	 * Creates a new chunk
	 */
	public Chunk(int m, int n, int biomeId) {
		this.pos = new Point(m, n);
		this.blocks = new AbstractBlock[Chunk.CHUNK_SIZE_XZ][Chunk.MAX_CHUNK_COORD_Y + 1][Chunk.CHUNK_SIZE_XZ];
		this.biomeId = biomeId;
		fillEmpty();
	}
	
	/**
	 * Returns the position of the chunk
	 * @return
	 */
	public Point getPosition() {
		return this.pos;
	}
	
	/**
	 * Returns the biome of the chunk
	 * @return
	 */
	public int getBiome() {
		return this.biomeId;
	}
	
	/**
	 * Returns the block-type at x|y|z (internal coordinates)
	 * @param x -16 .. +16
	 * @param y 0 .. 255
	 * @param z -16 .. +16
	 * @return BlockType
	 * @throws OutOfChunkBoundsException 
	 */
	public Class<?> getBlockTypeAtChunkCoord(int x, int y, int z) throws OutOfChunkBoundsException {
		if (!Chunk.inBoundsAsChunkCoord(x, y, z)) {
			throw new OutOfChunkBoundsException();
		}
		Point3D converted = Chunk.convertChunkCoordToChunkArrayCoord(x, y, z);
		return this.blocks[converted.X][converted.Y][converted.Z].getClass();
	}
	
	/**
	 * Returns the block-type at x|y|z (global coordinates)
	 * @param x
	 * @param y
	 * @param z
	 * @return BlockType
	 * @throws OutOfChunkBoundsException 
	 */
	public Class<?> getBlockTypeAtGlobalCoord(int x, int y, int z) throws OutOfChunkBoundsException {
		if (!Chunk.inBoundsAsGlobalCoord(this.pos.x, this.pos.y, x, y, z)) {
			throw new OutOfChunkBoundsException();
		}
		Point3D converted = Chunk.convertGlobalCoordToChunkArrayCoord(this.pos.x, this.pos.y, x, y, z);
		return this.blocks[converted.X][converted.Y][converted.Z].getClass();
	}
	
	/**
	 * Sets the block at x|y|z (internal coordinates) 
	 * @param block
	 * @param x -16 .. +16
	 * @param y 0 .. 255
	 * @param z -16 .. +16
	 * @throws OutOfChunkBoundsException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void setBlockAtChunkCoord(AbstractBlock block, int x, int y, int z) throws OutOfChunkBoundsException, InstantiationException, IllegalAccessException {
		if (!Chunk.inBoundsAsChunkCoord(x, y, z)) {
			throw new OutOfChunkBoundsException();
		}
		Point3D converted = Chunk.convertChunkCoordToChunkArrayCoord(x, y, z);
		this.blocks[converted.X][converted.Y][converted.Z] = block;
	}
	
	/**
	 * Sets the block at x|y|z (global coordinates)
	 * @param block
	 * @param x
	 * @param y
	 * @param z
	 * @throws OutOfChunkBoundsException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void setBlockAtGlobalCoord(AbstractBlock block, int x, int y, int z) throws OutOfChunkBoundsException, InstantiationException, IllegalAccessException {
		if (!Chunk.inBoundsAsGlobalCoord(this.pos.x, this.pos.y, x, y, z)) {
			throw new OutOfChunkBoundsException();
		}
		Point3D converted = Chunk.convertGlobalCoordToChunkArrayCoord(this.pos.x, this.pos.y, x, y, z);
		this.blocks[converted.X][converted.Y][converted.Z] = block;
	}
	
	public void fillChunkArrayCoordUp(Class<?> blocktype, int x, int yMax, int z) {
		for(int y = 0; y <= yMax; y++) {
			// TODO randomize fill method dirt-stones
			try {
				this.blocks[x][y][z] = (AbstractBlock) blocktype.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(69);
			}
		}
	}
	
	public void fillChunkUp(Class<?> blocktype, int yMax) {
		for(int x = 0; x < Chunk.CHUNK_SIZE_XZ; x++) {
			for(int z = 0; z < Chunk.CHUNK_SIZE_XZ; z++) {
				this.fillChunkArrayCoordUp(blocktype, x, yMax, z);
			}
		}
	}
	
	/**
	 * Returns the global coord of the block in that chunk at x|y|z 
	 * @param x -16 .. +16
	 * @param y 0 .. 255
	 * @param z -16 .. +16
	 * @return
	 * @throws OutOfChunkBoundsException 
	 */
	public Point3D getGlobalCoordOfChunkBlock(int x, int y, int z) throws OutOfChunkBoundsException {
		if (!Chunk.inBoundsAsChunkCoord(x, y, z)) {
			throw new OutOfChunkBoundsException();
		}
		Point3D chunkArrayCoord = Chunk.convertChunkCoordToChunkArrayCoord(x, y, z);
		return Chunk.convertChunkArrayCoordToGlobalCoord(this.pos.x, this.pos.y, chunkArrayCoord.X, y, chunkArrayCoord.Z);
	}
	
	public int getMaximumHeightAtChunkCoord(int x, int z) {
		int y = 255;
		try {
			while(this.getBlockTypeAtChunkCoord(x, y, z) == AirBlock.class) {
				y--;
			}
		} catch (OutOfChunkBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return y;
	}
	
	/**
	 * Checks if the given coord x|y|z (internal coordinates) are in range
	 * @param x
	 * @param y
	 * @param z
	 * @return boolean
	 */
	private static boolean inBoundsAsChunkCoord(int x, int y, int z) {
		boolean xOk = x >= Chunk.MAX_CHUNK_COORD_XZ * (-1.) && x <= Chunk.MAX_CHUNK_COORD_XZ;
		boolean yOk = y >= 0 && y <= Chunk.MAX_CHUNK_COORD_Y;
		boolean zOk = z >= Chunk.MAX_CHUNK_COORD_XZ * (-1.) && z <= Chunk.MAX_CHUNK_COORD_XZ;
		return xOk && yOk && zOk;
	}
	
	/**
	 * Checks if the given coord x|y|z (global coordinates) are inside the chunk m|n
	 * @param m
	 * @param n
	 * @param x
	 * @param y
	 * @param z
	 * @return boolean
	 */
	private static boolean inBoundsAsGlobalCoord(int m, int n, int x, int y, int z) {
		boolean xOk = x >= Chunk.MAX_CHUNK_COORD_XZ * (-1.) + m * Chunk.CHUNK_SIZE_XZ && x <= Chunk.MAX_CHUNK_COORD_XZ + m * Chunk.CHUNK_SIZE_XZ;
		boolean yOk = y >= 0 && y <= Chunk.MAX_CHUNK_COORD_Y;
		boolean zOk = z >= Chunk.MAX_CHUNK_COORD_XZ * (-1.) + n * Chunk.CHUNK_SIZE_XZ && z <= Chunk.MAX_CHUNK_COORD_XZ + n * Chunk.CHUNK_SIZE_XZ;
		return xOk && yOk && zOk;
	}
	
	/**
	 * Converts a chunk coord to a chunk array coord
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private static Point3D convertChunkCoordToChunkArrayCoord(int x, int y, int z) {
		return new Point3D(x + Chunk.MAX_CHUNK_COORD_XZ, y, Chunk.MAX_CHUNK_COORD_XZ - z);
	}
	
	/**
	 * Converts a chunk array coord to a global coord
	 * @param m
	 * @param n
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private static Point3D convertChunkArrayCoordToGlobalCoord(int m, int n, int x, int y, int z) {
		int xGlobal = x - Chunk.MAX_CHUNK_COORD_XZ + m * Chunk.CHUNK_SIZE_XZ;
		int yGlobal = y;
		int zGlobal = Chunk.MAX_CHUNK_COORD_XZ - z + n * Chunk.CHUNK_SIZE_XZ;
		return new Point3D(xGlobal, yGlobal, zGlobal);
	}
	
	/**
	 * Converts a global coord to a chunk array coord
	 * @param m
	 * @param n
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private static Point3D convertGlobalCoordToChunkArrayCoord(int m, int n, int x, int y, int z) {
		int xArray = x + Chunk.MAX_CHUNK_COORD_XZ - m * Chunk.CHUNK_SIZE_XZ;
		int yArray = y;
		int zArray = Chunk.MAX_CHUNK_COORD_XZ - z + n * Chunk.CHUNK_SIZE_XZ;
		return new Point3D(xArray, yArray, zArray);
	}
	
	private void fillEmpty() {
		for(int x = 0; x < Chunk.CHUNK_SIZE_XZ; x++) {
			for(int y = 0; y <= Chunk.MAX_CHUNK_COORD_Y; y++) {
				for(int z = 0; z < Chunk.CHUNK_SIZE_XZ; z++) {
					this.blocks[x][y][z] = new AirBlock();
				}
			}
		}
	}
}
