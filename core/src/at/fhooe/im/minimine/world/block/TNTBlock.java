package at.fhooe.im.minimine.world.block;

public class TNTBlock extends AbstractBlock {

	public TNTBlock() {
		super((byte)1);
		TNTBlock.inflammable = true;
		TNTBlock.destructable = true;
	}
	
	public static boolean isInflammable() {
		return TNTBlock.inflammable;
	}
	
	public static boolean isDestructable() {
		return TNTBlock.destructable;
	}

}
