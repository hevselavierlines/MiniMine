package at.fhooe.im.minimine.world.biome;

import java.util.Random;

public class Biomes {
	public static int HILLS = 0;
	
	private static byte[][] probabilityNeighbor = new byte[][] {
		// HILLS
		{  60 } // HILLS
	};
	
	/**
	 * Returns a possible neighbor for the given type 
	 * @param type
	 * @return
	 */
	public static int getNeighborType(int type) {
		int amount = Biomes.probabilityNeighbor.length;
		Random rand = new Random();
		
		int[] randomness = new int[100];
		int idx = 0;
		for (int i = 0; i < amount; i++) {
			for (int j = idx; j < idx + Biomes.probabilityNeighbor[type][i]; j++) {
				randomness[j] = i;
			}
			idx += Biomes.probabilityNeighbor[type][i];
		}
		
		return randomness[rand.nextInt(100)];
	}
}
