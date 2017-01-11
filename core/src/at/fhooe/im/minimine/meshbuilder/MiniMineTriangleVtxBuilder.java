package at.fhooe.im.minimine.meshbuilder;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Vector3;

/**
 * 
 * @author Christine
 *
 */
public class MiniMineTriangleVtxBuilder extends MiniMineAbstractTriangleMeshBuilder {
	
	private int attributes = VertexAttributes.Usage.Position;

	@Override
	public MiniMineMesh create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAttributes() {
		return attributes;
	}
	
	@Override
	protected void doAddVertex(Vector3 vtx) {
		// position 
		vertices.add(vtx.x);
		vertices.add(vtx.y);
		vertices.add(vtx.z);
	}

	@Override
	public Mesh createMesh() {
		float[] meshData = getVertices();
		short[] indicesData = getIndices();
		vertices.clear();
		Mesh mesh = new Mesh(true, meshData.length, indicesData.length, 
				new VertexAttribute(Usage.Position, 3, "a_position"));
		mesh.setVertices(meshData);
		mesh.setIndices(indicesData);
		return mesh;
	}

}
