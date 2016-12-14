#ifdef GL_ES 
precision mediump float;
#endif
 
varying vec2 v_texCoord0;
 
void main() {
    gl_FragColor = texture2d(v_texCoord0);
}