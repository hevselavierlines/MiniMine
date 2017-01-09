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
public class MiniMineTriangleVtxNrmUvColBuilder extends MiniMineAbstractTriangleMeshBuilder {
	
	private int attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal 
			| VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.ColorPacked;

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
		// normal
		vertices.add(normal.x);
		vertices.add(normal.y);
		vertices.add(normal.z);
		// uv coordinates
		vertices.add(u);
		vertices.add(v);
		// rgba color values
		vertices.add(color.r);
		vertices.add(color.g);
		vertices.add(color.b);
		vertices.add(color.a);
	}

	@Override
	public Mesh createMesh() {
		float[] meshData = getVertices();
		short[] indicesData = getIndices();
		vertices.clear();
		Mesh mesh = new Mesh(true, meshData.length, indicesData.length, 
				new VertexAttribute(Usage.Position, 3, "a_position"), 
				new VertexAttribute(Usage.Normal, 3, "a_normal"), 
				new VertexAttribute(Usage.TextureCoordinates, 2, "a_texcoords"),
				new VertexAttribute(Usage.ColorPacked, 4, "a_color"));
		mesh.setVertices(meshData);
		mesh.setIndices(indicesData);
		return mesh;
	}

}
