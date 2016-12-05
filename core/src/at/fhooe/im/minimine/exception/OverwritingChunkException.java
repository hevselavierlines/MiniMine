package at.fhooe.im.minimine.exception;

public class OverwritingChunkException extends Exception {
	
	private static final long serialVersionUID = -8465457865184397916L;

	public OverwritingChunkException() {
		super("This action would overwrite an existing chunk!");
	}

}
