//varying vec2 v_uv;
//varying vec3 v_normal;
//varying vec3 v_position;
//varying vec3 v_skinnedNormal;
//
//uniform sampler2D u_diffuseTexture;
//uniform sampler2D u_shadowRamp;
//uniform sampler2D u_specularMask;
//uniform sampler2D u_matcap;
//
//uniform vec3 u_lightDir;
//uniform vec3 u_lightColor;
//uniform vec3 u_shadowColor;
//uniform vec3 u_rimColor;
//uniform float u_rimPower; // Declared and now USED
//
//void main() {
//    // Base color
//    vec4 diffuse = texture2D(u_diffuseTexture, v_uv);
//
//    // Cel-shading
//    float NdotL = dot(v_normal, normalize(u_lightDir));
//    float lightIntensity = smoothstep(0.1, 0.5, NdotL);
//    vec3 lighting = mix(u_shadowColor, u_lightColor, lightIntensity);
//
//    // Artificial SSS
//    float sssWidth = 0.1;
//    float sss = smoothstep(0.2 - sssWidth, 0.2 + sssWidth, NdotL);
//    vec3 sssColor = mix(u_shadowColor * 1.2, u_lightColor * 0.8, sss);
//
//    // Specular highlights
//    float specularMask = texture2D(u_specularMask, v_uv).r;
//    vec3 viewDir = normalize(-v_position);
//    vec3 halfVec = normalize(u_lightDir + viewDir);
//    float specular = pow(max(dot(v_normal, halfVec), 0.0), 32.0) * specularMask;
//
//    // Rim lighting (now uses u_rimPower)
//    float rim = 1.0 - max(dot(viewDir, v_normal), 0.0);
//    rim = pow(rim, u_rimPower); // <-- Critical fix
//    rim = smoothstep(0.6, 0.8, rim);
//
//    // Matcap
//    vec3 matcap = texture2D(u_matcap, v_normal.xy * 0.5 + 0.5).rgb;
//
//    // Combine all elements
//    vec3 finalColor = diffuse.rgb * (lighting + sssColor + specular + rim * u_rimColor) * matcap;
//
//    gl_FragColor = vec4(finalColor, diffuse.a);
//}

//void main() {
//    vec4 diffuse = texture2D(u_diffuseTexture, v_uv);
//    vec3 testLightDir = normalize(vec3(0.0, 1.0, 0.0));
//    v_normal = normalize(vec4(v_skinnedNormal, 0.0).xyz);
//    float intensity = max(dot(v_normal, normalize(testLightDir)), 0.0);
//
//    // Simple two-tone cel-shading (shadow or light)
//
//    vec4 color;
//    if (intensity > 0.95) { // High intensity (highlight)
//        color = vec4(diffuse.rgb, 1.0); // Use object color
//    } else if (intensity > 0.5) { // Medium intensity (mid-tone)
//        color = vec4(diffuse.rgb * 0.7, 1.0); // Slightly darker object color
//    } else { // Low intensity (shadow)
//        color = vec4(diffuse.rgb * 0.3, 1.0); // Even darker object color
//    }
////    vec3 lighting = mix(u_shadowColor, u_lightColor, lightIntensity);
//    vec4 debugColor = vec4(0.0, 0.0, 0.0, 1.0); // Default black
//    if(intensity < 0.5) { //Modified for wider range
//        debugColor = vec4(1.0, 0.0, 0.0, 1.0); // Red for very low
//    }
//    else if(intensity < 0.7) {
//        debugColor = vec4(0.0, 1.0, 0.0, 1.0); // Green
//    }
//    else if(intensity < 0.9) {
//        debugColor = vec4(0.0, 0.0, 1.0, 1.0); // Blue
//    } else {
//        debugColor = vec4(1.0, 1.0, 1.0, 1.0); // White for high
//    }
//    vec4 finalColor = vec4(color.rgb, 1.0);
////    gl_FragColor = vec4(finalColor.rgb, diffuse.a);
//    gl_FragColor = vec4(v_normal * 0.5 + 0.5, 1.0);
//}

// SimpleCelFragment.glsl
#ifdef GL_ES
precision mediump float;
#endif

varying vec3 v_normal;
varying vec2 v_uv;

uniform vec3 u_lightDir;
uniform sampler2D u_diffuseTexture;
uniform sampler2D u_shadowRamp;


void main() {
    // Normalize the interpolated normal and the light direction.
    vec3 normal = normalize(v_normal);
    vec3 lightDir = normalize(u_lightDir);

    // Compute the light intensity using a dot product.
    float dotNL = dot(normal, lightDir);
    float rampCoord = dotNL * 0.5 + 0.5;

    vec4 rampColor = texture2D(u_shadowRamp, vec2(rampCoord, 0.0));
    // Discretize the intensity (cel shading steps).
//    float intensity;
//    if (dotNL > 0.5) {
//        intensity = 1.0; // bright highlight
//    } else if (dotNL > 0.1) {
//        intensity = 0.9; // mid-tone
//    } else {
//        intensity = 0.8; // shadow
//    }

    vec4 baseColor = texture2D(u_diffuseTexture, v_uv);
    vec3 finalColor = baseColor.rgb * rampColor.rgb;

    gl_FragColor = vec4(finalColor, baseColor.a);
}
