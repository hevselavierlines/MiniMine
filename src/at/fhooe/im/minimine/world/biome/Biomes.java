package at.fhooe.im.minimine.world.biome;

import java.util.Random;

public class Biomes {
	public final static int FIRST = -1;
	public final static int FLATLAND = 0;
	public final static int HILLS = 1;
	public final static int FOREST = 2;
	
	private static byte[][] probabilityNeighbor = new byte[][] {
		// FLATLAND, 	HILLS,	FOREST
		{  50,		    15,		35		 }, 	// FLATLAND
		{  70,			20,		10		 }, 	// HILLS
		{  25,			15,		60		 } 		// FOREST
	};
	
	
	/**
	 * Returns a possible neighbor for the given type 
	 * @param type
	 * @return
	 */
	public static int calculateRandomNeighborType(int type) {
		if (type == Biomes.FIRST) {
			Random rand = new Random();
			return rand.nextInt(3);
		}
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
