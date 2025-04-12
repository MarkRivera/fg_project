attribute vec3 a_position;
attribute vec3 a_normal;

uniform mat4 u_projViewTrans; // Camera's projection * view
uniform mat4 u_worldTrans;    // Model's world transform
uniform mat4 u_normalMatrix;  // Normal matrix (inverse transpose of model-view)
uniform float u_outlineThickness;

void main() {
    // Transform position to world space
    vec4 worldPos = u_worldTrans * vec4(a_position, 1.0);

    // Transform normal to world space using the normal matrix
    vec3 worldNormal = normalize((u_normalMatrix * vec4(a_normal, 0.0)).xyz);

    // Expand position along the normal
    worldPos.xyz += worldNormal * u_outlineThickness;

    // Final position in clip space
    gl_Position = u_projViewTrans * worldPos;
}
