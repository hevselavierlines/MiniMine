package at.fhooe.im.minimine.meshbuilder;

import at.fhooe.im.minimine.world.Chunk;

/**
 * 
 * @author Christine
 *
 */
public class MiniMineMeshBuilder {

	/** Constructor for a MeshBuilder object */
	public MiniMineMeshBuilder() {
		
	}
	
	
	public MiniMineMesh buildMesh(Chunk c) {
		MiniMineMesh mesh = new MiniMineMesh();
		mesh.setFaceSize(3);
		mesh.setVertexSize(3);
		
		double[] vertices = mesh.getVertices();
		vertices = new double[mesh.getVertexSize()];
		
		
		return mesh;
	}
}
