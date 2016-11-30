package at.fhooe.im.minimine.exception;

public class OutOfChunkBoundsException extends Exception {

	private static final long serialVersionUID = -7238259683059343426L;
	
	public OutOfChunkBoundsException() {
		super("The given coordinates are out of bounds of the chunk!");
	}

}
