package at.fhooe.im.minimine.character;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

public class InputManager extends InputAdapter{
	//Keybindings
	final int forward = Keys.W;
	final int backward = Keys.S;
	final int right = Keys.D;
	final int left = Keys.A;
	final int cameraChange = Keys.C;
	final int reset = Keys.R;
	public InputManager(){
		
	}
	
	public int AnyKeyPressed(){
		if(Gdx.input.isKeyPressed(forward)){
			return forward;	
		}if(Gdx.input.isKeyPressed(left)){
			return left;
		}if(Gdx.input.isKeyPressed(backward)){
			return backward;
		}if(Gdx.input.isKeyPressed(right)){
			return right;
		}if(Gdx.input.isKeyPressed(cameraChange)){
			return cameraChange;
		}if(Gdx.input.isKeyPressed(reset)){
			return reset;
		}
		else return 0;
	}
	public int[] MouseDelta(){
		Gdx.input.setCursorCatched(true);
		int mouseDelta[] = {Gdx.input.getDeltaX(),Gdx.input.getDeltaY()};
		
		return mouseDelta;	
	}
}
