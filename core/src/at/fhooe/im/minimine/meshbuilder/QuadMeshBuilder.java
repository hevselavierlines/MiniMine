package at.fhooe.im.minimine.meshbuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

/**
 * 
 * @author Christine
 *
 */
public interface QuadMeshBuilder {
	
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
	public void setUvCoords(double u, double v);
	
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
	 * @param idx4
	 */
	public void addQuad(int idx1, int idx2, int idx3, int idx4);
	
	/**
	 * 
	 * @return
	 */
	public int numVertices();
	
	/**
	 * 
	 * @return
	 */
	public int numQuads();
	
	/**
	 * 
	 */
	public void reset();
	
	/**
	 * 
	 * @return
	 */
	public Mesh create();
	
	
}
