package at.fhooe.im.minimine.world;

import java.awt.Point;

import at.fhooe.im.minimine.exception.OutOfChunkBoundsException;
import at.fhooe.im.minimine.world.block.AbstractBlock;

public class Chunk {

	private Point pos;
	private AbstractBlock[][][] blocks;
	
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
	public Chunk(int m, int n) {
		this.pos = new Point(m, n);
		this.blocks = new AbstractBlock[33][256][33];
	}
	
	/**
	 * Returns the position of the chunk
	 * @return
	 */
	public Point getPosition() {
		return this.pos;
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
	
	/**
	 * Checks if the given coord x|y|z (internal coordinates) are in range
	 * @param x
	 * @param y
	 * @param z
	 * @return boolean
	 */
	private static boolean inBoundsAsChunkCoord(int x, int y, int z) {
		boolean xOk = x >= -16 && x <= 16;
		boolean yOk = y >= 0 && y <= 255;
		boolean zOk = z >= -16 && z <= 16;
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
		boolean xOk = x >= -16 + m * 33 && x <= 16 + m * 33;
		boolean yOk = y >= 0 && y <= 255;
		boolean zOk = z >= -16 + n * 33 && z <= 16 + n * 33;
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
		return new Point3D(x + 16, y, 16 - z);
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
		int xGlobal = x - 16 + m * 33;
		int yGlobal = y;
		int zGlobal = 16 - z + n * 33;
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
		int xArray = x + 16 - m * 33;
		int yArray = y;
		int zArray = 16 - z + n * 33;
		return new Point3D(xArray, yArray, zArray);
	}
}
