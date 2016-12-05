package at.fhooe.im.minimine.world.block;

public class DirtBlock extends AbstractBlock {

	public DirtBlock() {
		super((byte)1);
		DirtBlock.inflammable = false;
		DirtBlock.destructable = true;
	}
	
	public static boolean isInflammable() {
		return DirtBlock.inflammable;
	}
	
	public static boolean isDestructable() {
		return DirtBlock.destructable;
	}

}
