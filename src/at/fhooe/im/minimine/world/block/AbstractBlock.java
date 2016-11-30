package at.fhooe.im.minimine.world.block;

public abstract class AbstractBlock {

	protected static boolean inflammable;
	protected static boolean destructable;
	private byte health;
	
	public AbstractBlock(byte health) {
		this.health = health;
	}
	
	public static boolean isInflammable() {
		return AbstractBlock.inflammable;
	}
	
	public static boolean isDestructable() {
		return AbstractBlock.destructable;
	}
	
	public byte getHealth() {
		return this.health;
	}
	
	public byte removeHealth(byte amount) {
		this.health -= amount;
		return this.health;
	}
	
}
