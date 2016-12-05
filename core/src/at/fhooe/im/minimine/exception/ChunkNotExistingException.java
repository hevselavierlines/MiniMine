package at.fhooe.im.minimine.exception;

public class ChunkNotExistingException extends Exception {
	
	private static final long serialVersionUID = 6535681618346380535L;

	public ChunkNotExistingException() {
		super("The accessed chunk is not existing!");
	}

}
