package at.fhooe.im.minimine.world;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import at.fhooe.im.minimine.exception.ChunkNotExistingException;
import at.fhooe.im.minimine.exception.OutOfChunkBoundsException;
import at.fhooe.im.minimine.exception.OverwritingChunkException;

public class World {

	private String name;
	private Map<Point, Chunk> chunks;
	
	/**
	 * Creates a new world with a set name
	 * @param name
	 */
	public World(String name) {
		this.name = name;
		this.chunks = new HashMap<Point, Chunk>();
	}
	
	/**
	 * Returns the name of the world
	 * @return String
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setrs the name of the world
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the chunk at the grid-position m|n
	 * @param m
	 * @param n
	 * @return Chunk
	 * @throws ChunkNotExistingException 
	 */
	public Chunk getChunk(int m, int n) throws ChunkNotExistingException {
		Chunk c = this.chunks.get(new Point(m, n));
		if (c == null) {
			throw new ChunkNotExistingException();
		}
		return c;
	}
	
	/**
	 * Returns the chunk which contains the block at x|y|z (global coord)
	 * @param m
	 * @param n
	 * @return Chunk
	 * @throws ChunkNotExistingException 
	 */
	public Chunk getChunkWithBlockAtGlobalCoord(int x, int y, int z) throws ChunkNotExistingException {
		int m = 0;
		if (x >= -16) { 
			m = (x + 16) / 33; 
		} else {
			m = (x * (-1) + 16) / 33;
			m *= (-1);
		}
		
		int n = 0;
		if (z >= -16) { 
			n = (z + 16) / 33; 
		} else {
			n = (z * (-1) + 16) / 33;
			n *= (-1);
		}
		
		return this.getChunk(m, n);
	}
	
	/**
	 * Returns the block-type at x|y|z
	 * @param x
	 * @param y
	 * @param z
	 * @return BlockType
	 * @throws OutOfChunkBoundsException 
	 * @throws ChunkNotExistingException 
	 */
	public Class<?> getBlockTypeAtGlobalCoord(int x, int y, int z) throws OutOfChunkBoundsException, ChunkNotExistingException {
		Chunk c = this.getChunkWithBlockAtGlobalCoord(x, y, z);
		return c.getBlockTypeAtGlobalCoord(x, y, z);
	}
	
	/**
	 * Adds a chunk safely
	 * @param c Chunk
	 * @throws OverwritingChunkException 
	 */
	public void addChunk(Chunk c) throws OverwritingChunkException {
		this.addChunk(c, true);
	}
	
	/**
	 * Adds a chunk
	 * @param c Chunk
	 * @param doSafe true = Cannot overwrite an existing chunk
	 * @throws OverwritingChunkException 
	 * @throws ChunkNotExistingException 
	 */
	public void addChunk(Chunk c, boolean doSafe) throws OverwritingChunkException {
		if (doSafe) {
			try {
				this.getChunk(c.getPosition().x, c.getPosition().y);
				// does not throw ChunkNotExistingException
				throw new OverwritingChunkException();
			} catch (ChunkNotExistingException e) {
				// chunk not existing
				this.chunks.put(new Point(c.getPosition().x, c.getPosition().y), c);
			}
		} else {
			this.chunks.put(new Point(c.getPosition().x, c.getPosition().y), c);
		}
	}
	
}
