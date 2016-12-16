package at.fhooe.im.minimine.meshbuilder;

import at.fhooe.im.minimine.world.Chunk;

/**
 * 
 * @author Christine
 *
 */
public class MeshBuilder {

	/** Constructor for a MeshBuilder object */
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
