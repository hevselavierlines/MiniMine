package at.fhooe.im.minimine.world.tester;

import java.util.Random;

import at.fhooe.im.minimine.exception.OverwritingChunkException;
import at.fhooe.im.minimine.world.Chunk;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.block.AirBlock;
import at.fhooe.im.minimine.world.block.DirtBlock;
import at.fhooe.im.minimine.world.block.StoneBlock;

public class BlockAccessTimeTester {
	
	private static World w;
	private static long amountFrames = 10000l;
	private static long recentTime;
	private static long startTime;
	private static long neededTimeInNanoSec;
	private static long longestFrameInMicroSec = 0l;
	private static long averageFrameInMicroSec = 0l;
	private static long shortestFrameInMicroSec = 100000000000000000l;
	private static long amountFramesBelow60Fps = 0l;
	private static long amountFramesBelow90Fps = 0l;
	private static long amountFramesBelow120Fps = 0l;
	private static long amountFramesBelow300Fps = 0l;
	private static long amountFramesBelow500Fps = 0l;
	private static long amountFramesBelow1000Fps = 0l;
	private static long amountFramesBelow5000Fps = 0l;

	public static void main(String[] args) {
		
		System.out.println("Init BlockAccessTimeTest...\n");
		BlockAccessTimeTester.init();
		
		System.out.println("Reading 33x256x33 BlockTypes per frame...");
		System.out.println("Running " + BlockAccessTimeTester.amountFrames + " frames...\n");
		int frame = 0;
		BlockAccessTimeTester.startTime = System.nanoTime();
		while (frame < BlockAccessTimeTester.amountFrames) {
			BlockAccessTimeTester.recentTime = System.nanoTime();
			BlockAccessTimeTester.run();
			BlockAccessTimeTester.neededTimeInNanoSec = System.nanoTime() - BlockAccessTimeTester.recentTime;
			if (BlockAccessTimeTester.neededTimeInNanoSec > BlockAccessTimeTester.longestFrameInMicroSec * 1000) {
				BlockAccessTimeTester.longestFrameInMicroSec = (long)(BlockAccessTimeTester.neededTimeInNanoSec / 1000.);
			}
			if (BlockAccessTimeTester.neededTimeInNanoSec < BlockAccessTimeTester.shortestFrameInMicroSec * 1000) {
				BlockAccessTimeTester.shortestFrameInMicroSec = (long)(BlockAccessTimeTester.neededTimeInNanoSec / 1000.);
			}
			if (BlockAccessTimeTester.neededTimeInNanoSec > 1000000000. / 60) {
				BlockAccessTimeTester.amountFramesBelow60Fps++;
			}
			if (BlockAccessTimeTester.neededTimeInNanoSec > 1000000000. / 90) {
				BlockAccessTimeTester.amountFramesBelow90Fps++;
			}
			if (BlockAccessTimeTester.neededTimeInNanoSec > 1000000000. / 120) {
				BlockAccessTimeTester.amountFramesBelow120Fps++;
			}
			if (BlockAccessTimeTester.neededTimeInNanoSec > 1000000000. / 300) {
				BlockAccessTimeTester.amountFramesBelow300Fps++;
			}
			if (BlockAccessTimeTester.neededTimeInNanoSec > 1000000000. / 500) {
				BlockAccessTimeTester.amountFramesBelow500Fps++;
			}
			if (BlockAccessTimeTester.neededTimeInNanoSec > 1000000000. / 1000) {
				BlockAccessTimeTester.amountFramesBelow1000Fps++;
			}
			if (BlockAccessTimeTester.neededTimeInNanoSec > 1000000000. / 5000) {
				BlockAccessTimeTester.amountFramesBelow5000Fps++;
			}
			BlockAccessTimeTester.averageFrameInMicroSec += (long)(BlockAccessTimeTester.neededTimeInNanoSec / 1000.);
			frame++;
		}
		BlockAccessTimeTester.averageFrameInMicroSec /= BlockAccessTimeTester.amountFrames;
		
		System.out.println(BlockAccessTimeTester.amountFrames + " frames in " + ((System.nanoTime() - BlockAccessTimeTester.startTime) / 1000000000.) + " seconds");
		System.out.println("Shortest: " + BlockAccessTimeTester.shortestFrameInMicroSec + " µs (" + (1000000. / BlockAccessTimeTester.shortestFrameInMicroSec) + " fps)");
		System.out.println("Average:  " + BlockAccessTimeTester.averageFrameInMicroSec + " µs (" + (1000000. / BlockAccessTimeTester.averageFrameInMicroSec) + " fps)");
		System.out.println("Longest:  " + BlockAccessTimeTester.longestFrameInMicroSec + " µs (" + (1000000. / BlockAccessTimeTester.longestFrameInMicroSec) + " fps)");
		System.out.println("Frames below  60 fps: " + BlockAccessTimeTester.amountFramesBelow60Fps + " (" + (BlockAccessTimeTester.amountFramesBelow60Fps * 100 / (double)BlockAccessTimeTester.amountFrames) + "%)");
		System.out.println("Frames below  90 fps: " + BlockAccessTimeTester.amountFramesBelow90Fps + " (" + (BlockAccessTimeTester.amountFramesBelow90Fps * 100 / (double)BlockAccessTimeTester.amountFrames) + "%)");
		System.out.println("Frames below 120 fps: " + BlockAccessTimeTester.amountFramesBelow120Fps + " (" + (BlockAccessTimeTester.amountFramesBelow120Fps * 100 / (double)BlockAccessTimeTester.amountFrames) + "%)");
		System.out.println("Frames below 300 fps: " + BlockAccessTimeTester.amountFramesBelow300Fps + " (" + (BlockAccessTimeTester.amountFramesBelow300Fps * 100 / (double)BlockAccessTimeTester.amountFrames) + "%)");
		System.out.println("Frames below 500 fps: " + BlockAccessTimeTester.amountFramesBelow500Fps + " (" + (BlockAccessTimeTester.amountFramesBelow500Fps * 100 / (double)BlockAccessTimeTester.amountFrames) + "%)");
		System.out.println("Frames below  1k fps: " + BlockAccessTimeTester.amountFramesBelow1000Fps + " (" + (BlockAccessTimeTester.amountFramesBelow1000Fps * 100 / (double)BlockAccessTimeTester.amountFrames) + "%)");
		System.out.println("Frames below  5k fps: " + BlockAccessTimeTester.amountFramesBelow5000Fps + " (" + (BlockAccessTimeTester.amountFramesBelow5000Fps * 100 / (double)BlockAccessTimeTester.amountFrames) + "%)");

	}
	
	public static void init() {
		BlockAccessTimeTester.longestFrameInMicroSec = 0l;
		BlockAccessTimeTester.averageFrameInMicroSec = 0l;
		BlockAccessTimeTester.shortestFrameInMicroSec = 100000000000000000l;
		BlockAccessTimeTester.amountFramesBelow60Fps = 0l;
		BlockAccessTimeTester.amountFramesBelow90Fps = 0l;
		BlockAccessTimeTester.amountFramesBelow120Fps = 0l;
		BlockAccessTimeTester.amountFramesBelow300Fps = 0l;
		BlockAccessTimeTester.amountFramesBelow500Fps = 0l;
		BlockAccessTimeTester.amountFramesBelow1000Fps = 0l;
		BlockAccessTimeTester.amountFramesBelow5000Fps = 0l;
		
		BlockAccessTimeTester.w = new World("Test");
		Chunk c = new Chunk(0, 0);
		
		Random r = new Random();
		int block = 0;
		for (int x = -16; x <= 16; x++) {
			for (int y = 0; y < 256; y++) {
				for (int z = -16; z <= 16; z++) {
					block = r.nextInt(3);
					try {
						switch(block) {
							case 0: c.setBlockAtChunkCoord(new AirBlock(), x, y, z); break;
							case 1: c.setBlockAtChunkCoord(new DirtBlock(), x, y, z); break;
							case 2: c.setBlockAtChunkCoord(new StoneBlock(), x, y, z); break;
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
						System.exit(1);
					}
				}
			}
		}
		
		try {
			BlockAccessTimeTester.w.addChunk(c);
		} catch (OverwritingChunkException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public static void run() {
		try {
			Chunk c = BlockAccessTimeTester.w.getChunk(0, 0);
			for (int x = -16; x <= 16; x++) {
				for (int y = 0; y < 256; y++) {
					for (int z = -16; z <= 16; z++) {
						try {
							c.getBlockTypeAtChunkCoord(x, y, z);
						} catch (Exception e) {
							System.out.println(e.getMessage());
							System.exit(1);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
}