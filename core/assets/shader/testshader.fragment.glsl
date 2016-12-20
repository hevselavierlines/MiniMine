varying vec2 v_texCoord;
varying vec3 v_pos;
varying vec3 v_normal;

uniform sampler2D s_texture;

//uniform float Ka;
//uniform float Kd;
//uniform float Ks;
//uniform float shininess;

uniform vec3 u_lightPos;
uniform vec3 u_diffuseColor;
uniform vec3 u_specularColor;

void main() {
	vec3 N = normalize(v_normal);
	vec3 L = normalize(u_lightPos - v_pos);

	float Ka = 0.5;
	float Kd = 1.0;
	float Ks = 0.1;
	float shininess = 1.0;
	float lambertian = max(dot(N, L), 0.0);

	float specular = 0.0;
	if(lambertian > 0.0) {
		vec3 R = reflect(-L, N);
		vec3 V = normalize(-v_pos);

		float specAngle = max(dot(R, V), 0.0);
		specular = pow(specAngle, shininess);
	}
	//gl_FragColor = vec4(u_ambientColor, 1.0);
	//gl_FragColor = texture2D( s_texture, v_texCoord);
	vec4 texture = texture2D(s_texture, v_texCoord);

	vec4 shine = vec4(
			Ka +
			Kd * lambertian * u_diffuseColor +
			Ks * specular * u_specularColor, 1.0);
	gl_FragColor = shine * texture;
    //gl_FragColor = vec4(Ks * specular * u_specularColor, 1.0) * texture2D( s_texture, v_texCoord) + vec4(0.1, 0.1, 0.1, 1.0);
}
