attribute vec3 a_position;
attribute vec3 a_normal;

uniform mat4 u_projViewTrans;
uniform mat4 u_worldTrans;
uniform float u_outlineThickness;

void main() {
    vec3 scaledPosition = a_position + a_normal * u_outlineThickness;
    gl_Position = u_projViewTrans * u_worldTrans * vec4(scaledPosition, 1.0);
}

