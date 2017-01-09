package at.fhooe.im.minimine.character;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;

public class MovementController {
	private Vector3 forward;
	private Vector3 direction;
	private InputManager inputManager;
	
	public MovementController(InputManager inputManager){
		this.inputManager = inputManager;

	}
	public Vector3 Move(){
		
		return direction;
	}

	public Vector3 Forward() {
		
		return forward;
	}
	
}
