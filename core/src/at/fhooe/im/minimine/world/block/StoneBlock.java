package at.fhooe.im.minimine.world.block;

public class StoneBlock extends AbstractBlock {

	public StoneBlock() {
		super((byte)3);
		StoneBlock.inflammable = false;
		StoneBlock.destructable = true;
	}
	
	public static boolean isInflammable() {
		return StoneBlock.inflammable;
	}
	
	public static boolean isDestructable() {
		return StoneBlock.destructable;
	}

}
