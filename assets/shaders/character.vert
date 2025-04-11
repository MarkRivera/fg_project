attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

varying vec2 v_uv;
varying vec3 v_normal;
varying vec3 v_position;

uniform mat4 u_projViewTrans;
uniform mat4 u_worldTrans;
uniform mat3 u_normalMatrix;

void main() {
    v_uv = a_texCoord0;
    v_normal = normalize(u_normalMatrix * a_normal);
    vec4 worldPos = u_worldTrans * vec4(a_position, 1.0);
    v_position = worldPos.xyz;
    gl_Position = u_projViewTrans * worldPos;
}
