package at.fhooe.im.minimine.world.block;

public class WoodBlock extends AbstractBlock {

	public WoodBlock() {
		super((byte)5);
		StoneBlock.inflammable = true;
		StoneBlock.destructable = true;
	}
	
	public static boolean isInflammable() {
		return StoneBlock.inflammable;
	}
	
	public static boolean isDestructable() {
		return StoneBlock.destructable;

	}

}

