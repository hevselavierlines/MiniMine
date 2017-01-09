package at.fhooe.im.minimine.meshbuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;

/**
 * 
 * @author Christine
 *
 */
public interface MiniMineTriangleMeshBuilder {

	/** 
	 * 
	 * @param nx
	 */
	public void setNormal(Vector3 nx);
	
	/**
	 * 
	 * @param c
	 */
	public void setColor(Color c);
	
	/**
	 * 
	 * @param u
	 * @param v
	 */
	public void setUvCoords(float u, float v);
	
	/**
	 * 
	 * @param vtx
	 */
	public void addVertex(Vector3 vtx);

	/**
	 * 
	 * @param idx1
	 * @param idx2
	 * @param idx3
	 */
	public void addTriangle(int idx1, int idx2, int idx3);
	
	/**
	 * 
	 * @return
	 */
	public int numVertices();
	
	/**
	 * 
	 * @return
	 */
	public int numTriangles();
	
	/**
	 * 
	 */
	public void reset();
	
	/**
	 * 
	 * @return
	 */
	public MiniMineMesh create();
	
	
	public Mesh createMesh();
	
}
