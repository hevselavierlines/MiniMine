package at.fhooe.im.minimine.meshbuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

/**
 * 
 * @author Christine
 *
 */
public abstract class AbstractQuadMeshBuilder implements QuadMeshBuilder {

	protected Vector3 normal;
	
	protected Color color;
	
	protected double u;
	
	protected double v;
	
	private int vtxCnt;
	
	private int faceCnt;
	
	@Override
	public int numVertices() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int numQuads() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void setNormal(Vector3 nx) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setUvCoords(double u, double v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addVertex(Vector3 vtx) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addQuad(int idx1, int idx2, int idx3, int idx4) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @param vtx
	 */
	protected void doAddVertex(Vector3 vtx) {
		
	}
	
	/**
	 * 
	 * @param idx1
	 * @param idx2
	 * @param idx3
	 * @param idx4
	 */
	protected void doAddQuad(int idx1, int idx2, int idx3, int idx4) {
		
	}
	
	/**
	 * 
	 */
	protected void doReset() {
		
	}
}
