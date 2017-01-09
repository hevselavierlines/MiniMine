package at.fhooe.im.minimine.meshbuilder;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

/**
 * 
 * @author Christine
 *
 */
public abstract class MiniMineAbstractTriangleMeshBuilder implements MiniMineTriangleMeshBuilder {

	protected Vector3 normal;
	
	protected Color color;
	
	protected float u;
	
	protected float v;
	
	private int vtxCnt;
	
	private int faceCnt;
	
	protected ArrayList<Float> vertices;
	protected ArrayList<Integer> indices;
	
	public MiniMineAbstractTriangleMeshBuilder() {
		vertices = new ArrayList<Float>();
		indices = new ArrayList<Integer>();
	}
	
	
	@Override
	public int numVertices() {
		return vtxCnt;
	}
	
	@Override
	public int numTriangles() {
		return faceCnt;
	}
	
	@Override
	public void setNormal(Vector3 nx) {
		normal = nx;
	}
	
	@Override
	public void setColor(Color c) {
		color = c;
	}
	
	@Override
	public void setUvCoords(float u, float v) {
		this.u = u;
		this.v = v;
	}
	
	@Override
	public void addVertex(Vector3 vtx) { // TODO position, normals, uv, color hinzufügen
		doAddVertex(vtx);
		vtxCnt++;
	}
	
	@Override
	public void addTriangle(int idx1, int idx2, int idx3) {
		doAddTriangle(idx1, idx2, idx3);
		faceCnt++;
	}
	
	@Override
	public void reset() {
		doReset();
	}
	
	/**
	 * 
	 * @param vtx
	 */
	protected void doAddVertex(Vector3 vtx) {
		vertices.add(vtx.x);
		vertices.add(vtx.y);
		vertices.add(vtx.z);
		// TODO add position normals etc 
	}
	
	/**
	 * 
	 * @param idx1
	 * @param idx2
	 * @param idx3
	 */
	protected void doAddTriangle(int idx1, int idx2, int idx3) {
		indices.add(idx1);
		indices.add(idx2);
		indices.add(idx3);
	}
	
	/**
	 * 
	 */
	protected void doReset() {
		normal = null;
		color = null; 
		u = 0;
		v = 0;
		
	}
	
	abstract public int getAttributes();
	
	public float[] getVertices() {
		float[] vertexArray = new float[vertices.size()];
		for (int i = 0; i < vertexArray.length; i++) {
			vertexArray[i] = vertices.get(i);
		}
		return vertexArray;
	}
	
	public short[] getIndices() {
		short[] indicesArray = new short[indices.size()];
		for (int i = 0; i < indicesArray.length; i++) {
			indicesArray[i] = indices.get(i).shortValue();
		}
		return indicesArray;
	}
	
}
