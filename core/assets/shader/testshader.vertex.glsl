//our attributes
attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texcoords;

varying vec2 v_texCoord;

//our camera matrix
uniform mat4 u_projTrans;

void main() {
	v_texCoord = a_texcoords;
    gl_Position = u_projTrans * vec4(a_position, 1.0);
}

