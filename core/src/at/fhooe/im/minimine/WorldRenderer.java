package at.fhooe.im.minimine;

import java.awt.Point;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import at.fhooe.im.minimine.exception.ChunkNotExistingException;
import at.fhooe.im.minimine.exception.OverwritingChunkException;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.block.DirtBlock;

/**
 * The world render is important to get the chunks for the current position and it's surrounding.
 * 
 * @author Manuel Baumgartner
 *
 */
public class WorldRenderer {
	private Point currPos;
	private int bufferUsage;
	private Point[] loadedPoints;
	private Mesh[] loadedMeshes;
	private World world;
	private final int MAX_BUFFER;
	private ChunkMeshGenerator cmg;
	
	/**
	 * Creating a new world render with the world object.
	 * @param _world the world object. This world is going to be rendered
	 * @param maxBuffer the maximum size of chunks being kept in memory. 
	 * More than 15 elements is not possible due to memory issues.
	 */
	public WorldRenderer(World _world, int maxBuffer) {
		if(maxBuffer > 15) {
			maxBuffer = 15;
		}
		MAX_BUFFER = maxBuffer;
		currPos = new Point(0,0);
		bufferUsage = 0;
		loadedPoints = new Point[MAX_BUFFER];
		loadedMeshes = new Mesh[MAX_BUFFER];
		world = _world;
		cmg = new ChunkMeshGenerator();
	}
	
	/**
	 * This function loads the mesh at the certain position.
	 * It will be automatically added to the internal array of meshes.
	 * @param posX world position in x direction (0 = 0, 1 = 33, ...)
	 * @param posY world position in y direction (0 = 0, 1 = 33, ...)
	 */
	public void loadMeshAt(int posX, int posY) {
		Point loadPos = new Point(posX, posY);
		currPos = loadPos;
		int meshPos = -1;
		for(int i = 0; i < MAX_BUFFER; i++) {
			if(loadedPoints[i] != null && loadedPoints[i].equals(loadPos)) {
				meshPos = i;
			}
		}
		if(meshPos >= 0) {
			//chunk is already loaded and available.
			//loadedMeshes[meshPos];
		} else {
			if(loadedPoints[bufferUsage] == null) {
				loadMeshAtPosition(loadPos, bufferUsage);
				bufferUsage++;
				if(bufferUsage >= MAX_BUFFER) {
					bufferUsage = 0;
				}
			} else {
				//check for free space
				int freeSpace = -1;
				for(int i = 0; i < MAX_BUFFER; i++) {
					if(loadedPoints[i] == null) {
						freeSpace = i;
					}
				}
				if(freeSpace == -1) {
					freeSpace = cleanUp();
				}
				
				loadMeshAtPosition(loadPos, freeSpace);
				bufferUsage = freeSpace;
			}
		}
	}
	/**
	 * In case of invalidation the chunk can be reloaded.
	 * @param reloadPos The position of reload.
	 */
	public void reloadChunk(Point reloadPos) {
		int meshPos = -1;
		for(int i = 0; i < MAX_BUFFER; i++) {
			if(loadedPoints[i] != null && loadedPoints[i].equals(reloadPos)) {
				meshPos = i;
			}
		}
		if(meshPos >= 0) {
			loadedMeshes[meshPos].dispose();
			loadedMeshes[meshPos] = null;
			bufferUsage = meshPos;
		}
		loadMeshAt(reloadPos.x, reloadPos.y);
	}
	/**
	 * Adapter between the world renderer and the mesh generator.
	 * @param worldPos world coordinates (0 = 0, 1 = 33, ...)
	 * @param bufferPos position in the buffer (array).
	 * @return the loaded mesh.
	 */
	private Mesh loadMeshAtPosition(Point worldPos, int bufferPos) {
		Chunk chunk = null;
		Mesh mesh = null;
		try {
			chunk = world.getChunk(worldPos.x, worldPos.y);
			
			//TODO call here the real mesh generator
			mesh = cmg.generateChunk(chunk, worldPos.x, worldPos.y);
			loadedMeshes[bufferPos] = mesh;
			loadedPoints[bufferPos] = currPos;
		} catch (ChunkNotExistingException e) {
			//TODO generate new chunk
			chunk = new Chunk(worldPos.x, worldPos.y);
			chunk.fillChunkUp(DirtBlock.class, 5);
			try {
				world.addChunk(chunk);
			} catch (OverwritingChunkException e1) {
				e1.printStackTrace();
			}
			mesh = cmg.generateChunk(chunk, worldPos.x, worldPos.y);
			loadedMeshes[bufferPos] = mesh;
			loadedPoints[bufferPos] = currPos;
		}
		
		return mesh;
	}
	/**
	 * When more then {@code MAX_BUFFER} elements are inserted all elements that are the farthest away from the current chunk will be removed. 
	 * This means that the mesh is removed. The world remains untouched.
	 * @return
	 */
	private int cleanUp() {
		System.out.println("cleanUp");
		int lastFreePos = 0;
		for(int i = 0; i < MAX_BUFFER; i++) {
			Point point = loadedPoints[i];
			if(point != null) {
				if(point.distance(currPos) > 2) {
					loadedPoints[i] = null;
					loadedMeshes[i].dispose();
					loadedMeshes[i] = null;
					lastFreePos = i;
				}
			}
		}
		return lastFreePos;
	}
	/**
	 * Render the current meshes.
	 * All chunks that are in memory will be loaded.
	 * @param _shader the shader for the rendering.
	 */
	public void render(ShaderProgram _shader) {
		for(int i = 0; i < MAX_BUFFER; i++) {
			Mesh mesh = loadedMeshes[i];
			if(mesh != null) {
				mesh.render(_shader, GL20.GL_TRIANGLES, 0, mesh.getMaxVertices());
			}
		}
	}
}
