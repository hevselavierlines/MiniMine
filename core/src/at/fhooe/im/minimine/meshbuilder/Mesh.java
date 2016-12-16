package at.fhooe.im.minimine.meshbuilder;

/**
 * 
 * @author Christine
 *
 */
public class Mesh {

	/** 
	 * Contains the vertices of the mesh.
	 * One block has 8 vertices. 
	 */
	private double[] vertices;
	
	/** 
	 * Contains the faces of the mesh.
	 * One block has 6 faces.
	 */
	private int[] faces;
	
	/** 
	 * Contains the size of one vertex in the vertices array.
	 * Example: one vertex consits of 3 double values (x, y, z) so the 
	 * vertexSize is 3. If it contains also the normals for x, y, z 
	 * the vertexSize would be 6.
	 */
	private int vertexSize;
	
	/**
	 * Contains the size of one face of the mesh. 
	 * Example: for a triangle mesh it is 3, for a quad mesh 4
	 */
	private int faceSize;
	
	/** Constructor for a mesh object */
	public Mesh() {
		
	}
	
	
	

	public double[] getVertices() {
		return vertices;
	}

	public void setVertices(double[] vertices) {
		this.vertices = vertices;
	}

	public int[] getFaces() {
		return faces;
	}

	public void setFaces(int[] faces) {
		this.faces = faces;
	}

	public int getVertexSize() {
		return vertexSize;
	}

	public void setVertexSize(int vertexSize) {
		this.vertexSize = vertexSize;
	}

	public int getFaceSize() {
		return faceSize;
	}

	public void setFaceSize(int faceSize) {
		this.faceSize = faceSize;
	}
	
	
}
