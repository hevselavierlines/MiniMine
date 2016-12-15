varying vec2 v_texCoord;
varying vec3 v_normal;

uniform sampler2D s_texture;
uniform vec3 u_lightPos;

void main() {

    gl_FragColor = texture2D( s_texture, v_texCoord);
}
