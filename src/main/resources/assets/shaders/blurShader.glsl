// blurShader.glsl

#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;

const float blurSize = 1.0;

void main() {
    vec4 sum = vec4(0.0);

    // Sample the texture multiple times with offsets to create blur
    sum += texture2D(u_texture, v_texCoords - 4.0 * blurSize);
    sum += texture2D(u_texture, v_texCoords - 3.0 * blurSize);
    sum += texture2D(u_texture, v_texCoords - 2.0 * blurSize);
    sum += texture2D(u_texture, v_texCoords - 1.0 * blurSize);
    sum += texture2D(u_texture, v_texCoords);
    sum += texture2D(u_texture, v_texCoords + 1.0 * blurSize);
    sum += texture2D(u_texture, v_texCoords + 2.0 * blurSize);
    sum += texture2D(u_texture, v_texCoords + 3.0 * blurSize);
    sum += texture2D(u_texture, v_texCoords + 4.0 * blurSize);

    gl_FragColor = sum / 9.0; // Take average to blur
}
