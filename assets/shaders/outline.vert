attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;
attribute vec2 a_boneWeight0;
attribute vec2 a_boneWeight1;
attribute vec2 a_boneWeight2;
attribute vec2 a_boneWeight3;

uniform mat4 u_projViewTrans;
uniform mat4 u_worldTrans;
uniform mat3 u_normalMatrix;
uniform float u_outlineThickness;
uniform mat4 u_bones[75];
uniform vec3 u_cameraPos;

varying vec2 v_texCoord;

void main() {
    // Skinning
    mat4 skinning = mat4(0.0);
    skinning += a_boneWeight0.y * u_bones[int(a_boneWeight0.x)];
    skinning += a_boneWeight1.y * u_bones[int(a_boneWeight1.x)];
    skinning += a_boneWeight2.y * u_bones[int(a_boneWeight2.x)];
    skinning += a_boneWeight3.y * u_bones[int(a_boneWeight3.x)];

    // Apply skinning and world transform
    vec4 skinnedPosition = skinning * vec4(a_position, 1.0);
    vec3 skinnedNormal = (skinning * vec4(a_normal, 0.0)).xyz;

    // World space position and normal
    vec4 worldPos = u_worldTrans * skinnedPosition;
    vec3 worldNormal = normalize(u_normalMatrix * skinnedNormal);

    // Compute view direction in world space
    vec3 viewDir = normalize(u_cameraPos - worldPos.xyz);

    // Compute dot product: a value < 0 means the normal is mostly opposite the view direction
    float dotNV = dot(worldNormal, viewDir);

    // Determine extrusion only if the normal is not facing the camera (dot < 0, approx. > 90° angle)
    // The step function returns 1.0 if dotNV is less than 0.0.
    float extrusion = u_outlineThickness * step(dotNV, 0.0);

    // Extrude vertex along the normal based on the computed extrusion factor
    worldPos.xyz += worldNormal * extrusion;

    gl_Position = u_projViewTrans * worldPos;
    v_texCoord = a_texCoord0;
}
