package at.fhooe.im.minimine.world.block;

public class TNTBlock extends AbstractBlock {

	public TNTBlock() {
		super((byte)4);
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
