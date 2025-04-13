// SimpleCelVertex.glsl
attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

uniform mat4 u_projViewTrans;
uniform mat4 u_worldTrans;
uniform mat3 u_normalMatrix;

varying vec3 v_normal;
varying vec2 v_uv;

attribute vec2 a_boneWeight0;
attribute vec2 a_boneWeight1;
attribute vec2 a_boneWeight2;
attribute vec2 a_boneWeight3;
uniform mat4 u_bones[75];

void main() {
    // Skinning
    mat4 skinning = mat4(0.0);
    skinning += a_boneWeight0.y * u_bones[int(a_boneWeight0.x)];
    skinning += a_boneWeight1.y * u_bones[int(a_boneWeight1.x)];
    skinning += a_boneWeight2.y * u_bones[int(a_boneWeight2.x)];
    skinning += a_boneWeight3.y * u_bones[int(a_boneWeight3.x)];

    vec4 skinnedPosition = skinning * vec4(a_position, 1.0);
    vec3 skinnedNormal = (skinning * vec4(a_normal, 0.0)).xyz;

    v_uv = a_texCoord0;
    // Transform vertex position into world space.
    vec4 worldPos = u_worldTrans * skinnedPosition;

//    v_position = worldPos.xyz;

    gl_Position = u_projViewTrans * worldPos;

    // Transform normal. Here we assume a_worldTrans is mostly a rotation and uniform scale.
    // For non-uniform scales, compute the normal matrix as the inverse-transpose of u_worldTrans.
    v_normal = u_normalMatrix * skinnedNormal;
}

