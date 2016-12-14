package at.fhooe.im.minimine.meshbuilder;

public class Mesh {

	private double[] vertices;
	private int[] faces;
	
	private int vertexSize;
	private int faceSize;
	
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
