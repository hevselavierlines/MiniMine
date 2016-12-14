package at.fhooe.im.minimine.meshbuilder;

import at.fhooe.im.minimine.world.Chunk;

public class MeshBuilder {

	public MeshBuilder() {
		
	}
	
	public Mesh buildMesh(Chunk c) {
		Mesh mesh = new Mesh();
		mesh.setFaceSize(3);
		mesh.setVertexSize(3);
		
		double[] vertices = mesh.getVertices();
		vertices = new double[mesh.getVertexSize()];
		
		
		return mesh;
	}
}
