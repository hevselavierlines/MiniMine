package at.fhooe.im.minimine.world.block;

public class AirBlock extends AbstractBlock {

	public AirBlock() {
		super((byte)0);
		AirBlock.inflammable = false;
		AirBlock.destructable = false;
	}
	
	public static boolean isInflammable() {
		return AirBlock.inflammable;
	}
	
	public static boolean isDestructable() {
		return AirBlock.destructable;
	}

}
