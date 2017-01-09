package at.fhooe.im.minimine.world.biome;

import java.util.Random;

public class Biomes {
	public final static int FIRST = -1;
	public final static int FLATLAND = 0;
	public final static int UPHILL = 1;
	public final static int DOWNHILL = 2;
	public final static int FOREST = 3;
	
	private static byte[][] probabilityNeighbor = new byte[][] {
		// FLATLAND,	UPHILL,		DOWNHILL,	FOREST
		{  	50,			15,			5,			30 		},	// FLATLAND
		{  	20,			20,			50,			10		},	// UPHILL
		{	50,			5,			5,			40		}, 	// DOWNHILL
		{  	25,			15,			5,			55		}	// FOREST
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
