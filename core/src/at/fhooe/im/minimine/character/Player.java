package at.fhooe.im.minimine.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import at.fhooe.im.minimine.character.InputManager;
import at.fhooe.im.minimine.exception.ChunkNotExistingException;
import at.fhooe.im.minimine.exception.OutOfChunkBoundsException;
import at.fhooe.im.minimine.world.World;
import at.fhooe.im.minimine.world.block.AirBlock;

public class Player {

	public Vector3 position;
	private Vector3 forward;
	private Vector3 right;
	private Vector3 up; 
	private boolean camState;
	private Vector3 direction = new Vector3(1,0,0);
	private float speed;
	private Model model;
	private ModelInstance instance;
	private ModelBatch modelBatch;
	private Mesh mesh;
	private Vector3 camOffsetTp = new Vector3(-8f,0f,0);
	private Vector3 camOffsetFp = new Vector3(-0f,0f,0f);
	private float degreesPerPixel = 0.5f;
	private Ray collisionCheck;
	Camera cam;
	InputManager inputManager;
	//MovementController movementController;
	private int timer;
	float deltaX; 
	float deltaY;
	private Vector3 camOffset;
	private float spdMod = 0.1f;
	public World world;
	
	public Player(Camera cam, World world){
		this.cam = cam;
		this.world = world;
		position = new Vector3(0f, 40f, 0f);
		up = new Vector3(0,1,0);
		right = new Vector3();
		collisionCheck = new Ray();
		cam.position.set((new Vector3().set(position)).add(camOffsetTp));
		cam.lookAt(position);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
		
        camState = true;
		ModelBuilder modelBuilder = new ModelBuilder();
		forward = new Vector3(1f,0f,0f);
		
		model = modelBuilder.createBox(1f, 2f,1f,new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal);
		instance = new ModelInstance(model);
		for(int i = 0; i < instance.model.meshes.size; i++) {
			Mesh mesh = instance.model.meshes.get(i);
			Matrix4 tempmat = new Matrix4();
			tempmat.setTranslation(position);
			mesh.transform(tempmat);
			
		}		
	}
	public void Move(InputManager inputManager) throws OutOfChunkBoundsException, ChunkNotExistingException{
		direction.setZero();
		Vector3 temp = new Vector3(0,0,0);
		
		deltaX = Math.min(-inputManager.MouseDelta()[0] * degreesPerPixel,5);
		
		deltaY = -inputManager.MouseDelta()[1] * degreesPerPixel;
		if(deltaY>=0){
			deltaY = Math.min(deltaY, 10);
		}else if(deltaY < 0){
			deltaY = Math.max(deltaY, -10);
		}
		forward.rotate(up, deltaX).nor();
		cam.rotateAround(position, up, deltaX);
		right.set(forward).crs(up).nor();
		if(!((forward.y<-0.95 && deltaY<0) || (forward.y > 0.95 && deltaY > 0))){
			forward.rotate(right, deltaY);
			cam.rotateAround(position,  right, deltaY);
		}
		if(inputManager.AnyKeyPressed() == inputManager.forward){
			temp.set(forward);
			direction.set(temp).nor();
			temp.setZero();
			
		}if(inputManager.AnyKeyPressed() == inputManager.backward){
			temp.set(forward).scl(-1);	
			direction.set(temp).nor();
			temp.setZero();	
		}if(inputManager.AnyKeyPressed() == inputManager.left){
			temp.set(forward).crs(up).scl(-1);
			direction.set(temp).nor();
			temp.setZero();
		}if(inputManager.AnyKeyPressed() == inputManager.right){
			temp.set(forward).crs(up);	
			direction.set(temp).nor();
			temp.setZero();
		}if(inputManager.AnyKeyPressed() == inputManager.cameraChange && timer>10){
			changeCamera();
		}if(inputManager.AnyKeyPressed() == inputManager.reset ){
			resetPlayer();
		}
		direction.nor().scl(0.3f).clamp(0, 0.5f);
		if(!world.getBlockTypeAtGlobalCoord((int)(position.x+direction.x), (int)(position.y+direction.y), (int)(position.z+direction.z)).getName().equals("at.fhooe.im.minimine.world.block.AirBlock")){
			System.out.println(world.getBlockTypeAtGlobalCoord((int)(position.x+direction.x), (int)(position.y+direction.y), (int)(position.z+direction.z)).getName());
			//direction.nor().scl(0.3f).clamp(0, 0.5f);
			direction.set(0,0,0);
			
		}
		
		position.add(new Vector3(direction.x, direction.y, direction.z));
		cam.position.set(position);
		cam.lookAt(new Vector3().set(position).add(forward));
		
		//System.out.println(world.getBlockTypeAtGlobalCoord((int) (position.x + direction.x),(int) (position.y+direction.y),(int) (position.z + direction.z)));
		Matrix4 tempmat = new Matrix4();
		tempmat.setToTranslation(new Vector3(direction.x, direction.y, direction.z));
		for (int i= 0 ; i< instance.model.meshes.size; i++){
			Mesh mesh = instance.model.meshes.get(i);
			//System.out.println(up.toString());
			
			tempmat.setToTranslation(new Vector3(direction.x,direction.y,direction.z));
			mesh.transform(tempmat);
			tempmat.set(new Matrix4());
		}
		timer++;
	}
	public void render(ShaderProgram _shader) {
		
		if(camState){
			camOffset = camOffsetFp;
			cam.position.add(new Vector3().set(forward).scl(-0.9f));
			cam.position.add(new Vector3(0,1,0));
			cam.position.add(camOffset);	
			cam.update();
		}else {
			camOffsetTp = new Vector3().set(forward).scl(-4);
			camOffset = camOffsetTp;
			cam.position.add(camOffset);	
			cam.update();	
			for(int i = 0; i < instance.model.meshes.size; i++) {
				Mesh mesh = instance.model.meshes.get(i);
				mesh.render(_shader, GL20.GL_TRIANGLES, 0, mesh.getMaxVertices());
			}
		}
	}
	public void dispose(){
		model.dispose();
	}
	public boolean changeCamera(){
		timer=0;
		camState = !camState;
		//false for third person camera
		//true for first person camera
		return camState;
	}
	public void resetPlayer(){
		forward.set(1,0,0);
		position.set(0,50,0);
	}
}
