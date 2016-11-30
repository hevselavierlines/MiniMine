package at.fhooe.im.minimine.world.tester;

import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.block.DirtBlock;
import at.fhooe.im.minimine.world.block.StoneBlock;

public class GlobalCoordToChunkToBlockTester {

	public static void main(String[] args) {
		System.out.println("Creating World ...");
		World w = new World("Test");
		
		System.out.println("Creating Chunks ...");
		Chunk c00 = new Chunk(0, 0);
		Chunk c10 = new Chunk(1, 0);
		Chunk c11 = new Chunk(1, 1);
		Chunk c0n1 = new Chunk(0, -1);
		Chunk cn1n1 = new Chunk(-1, -1);
		try {
			System.out.println("Adding test-blocks to chunk   0 |  0 ...");
			c00.setBlockAtChunkCoord(new DirtBlock(), 0, 0, 0);			// should be global coord:   0 |  0 |   0
			c00.setBlockAtChunkCoord(new StoneBlock(), 12, 7, -3); 		// should be global coord:  12 |  7 |  -3
			
			System.out.println("Adding test-blocks to chunk   1 |  0 ...");
			c10.setBlockAtChunkCoord(new DirtBlock(), -8, 12, 6);		// should be global coord:  25 | 12 |   6
			c10.setBlockAtChunkCoord(new StoneBlock(), 4, 8, -1); 		// should be global coord:  37 |  8 |  -1
			
			System.out.println("Adding test-blocks to chunk   1 |  1 ...");
			c11.setBlockAtChunkCoord(new DirtBlock(), -2, 6, -1);		// should be global coord:  31 |  6 |  32
			c11.setBlockAtChunkCoord(new StoneBlock(), 3, 5, 11); 		// should be global coord:  36 |  5 |  44
			
			System.out.println("Adding test-blocks to chunk   0 | -1 ...");
			c0n1.setBlockAtChunkCoord(new DirtBlock(), 5, 18, 10);		// should be global coord:   5 | 18 | -23
			c0n1.setBlockAtChunkCoord(new StoneBlock(), -16, 2, -2);	// should be global coord: -16 |  2 | -35
			
			System.out.println("Adding test-blocks to chunk  -1 | -1 ...");
			cn1n1.setBlockAtChunkCoord(new DirtBlock(), 7, 8, -15);		// should be global coord: -26 |  8 | -48
			cn1n1.setBlockAtChunkCoord(new StoneBlock(), -11, 6, 16); 	// should be global coord: -44 |  6 | -17
			
			System.out.println("Adding chunks to world ...");
			w.addChunk(c00);
			w.addChunk(c10);
			w.addChunk(c11);
			w.addChunk(c0n1);
			w.addChunk(cn1n1);
			//w.addChunk(c00);			would throw overwrite exception
			//w.addChunk(c00, false);	would NOT throw overwrite exception and overwrite chunk
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		try {
			System.out.println("Reading block-types of test-blocks in different chunks via global coordinates ...");
			Class<?> blockType = w.getBlockTypeAtGlobalCoord(0, 0, 0);
			System.out.println(blockType.toString());
			
			blockType = w.getBlockTypeAtGlobalCoord(12, 7, -3);
			System.out.println(blockType.toString());
			
			blockType = w.getBlockTypeAtGlobalCoord(25, 12, 6);
			System.out.println(blockType.toString());
			
			blockType = w.getBlockTypeAtGlobalCoord(37, 8, -1);
			System.out.println(blockType.toString());
			
			blockType = w.getBlockTypeAtGlobalCoord(31, 6, 32);
			System.out.println(blockType.toString());
			
			blockType = w.getBlockTypeAtGlobalCoord(36, 5, 44);
			System.out.println(blockType.toString());
			
			blockType = w.getBlockTypeAtGlobalCoord(5, 18, -23);
			System.out.println(blockType.toString());
			
			blockType = w.getBlockTypeAtGlobalCoord(-16, 2, -35);
			System.out.println(blockType.toString());
			
			blockType = w.getBlockTypeAtGlobalCoord(-26, 8, -48);
			System.out.println(blockType.toString());
			
			blockType = w.getBlockTypeAtGlobalCoord(-44, 6, -17);
			System.out.println(blockType.toString());
			
			System.out.println("\nSUCCESS");
		} catch (Exception e) {
			System.out.println("\nERROR: Reading of blocks went wrong!");
		}
	}

}
