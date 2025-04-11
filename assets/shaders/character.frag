varying vec2 v_uv;
varying vec3 v_normal;
varying vec3 v_position;

uniform sampler2D u_diffuseTexture;
uniform sampler2D u_shadowRamp;
uniform sampler2D u_specularMask;
uniform sampler2D u_matcap;

uniform vec3 u_lightDir;
uniform vec3 u_lightColor;
uniform vec3 u_shadowColor;
uniform vec3 u_rimColor;
uniform float u_rimPower;

void main() {
    // Base color
    vec4 diffuse = texture2D(u_diffuseTexture, v_uv);

    // Cel-shading
    float NdotL = dot(v_normal, normalize(u_lightDir));
    float lightIntensity = smoothstep(0.1, 0.2, NdotL);
    vec3 lighting = mix(u_shadowColor, u_lightColor, lightIntensity);

    // Artificial SSS
    float sssWidth = 0.1;
    float sss = smoothstep(0.2 - sssWidth, 0.2 + sssWidth, NdotL);
    vec3 sssColor = mix(u_shadowColor * 1.2, u_lightColor * 0.8, sss);

    // Specular highlights
    float specularMask = texture2D(u_specularMask, v_uv).r;
    vec3 viewDir = normalize(-v_position);
    vec3 halfVec = normalize(u_lightDir + viewDir);
    float specular = pow(max(dot(v_normal, halfVec), 32.0)) * specularMask;

    // Rim lighting
    float rim = 1.0 - max(dot(viewDir, v_normal), 0.0);
    rim = smoothstep(0.6, 0.8, rim);

    // Matcap
    vec3 matcap = texture2D(u_matcap, v_normal.xy * 0.5 + 0.5).rgb;

    // Combine all elements
    vec3 finalColor = diffuse.rgb * (lighting + sssColor + specular + rim * u_rimColor) * matcap;

    gl_FragColor = vec4(finalColor, diffuse.a);
}
