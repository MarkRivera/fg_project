package io.github.fg_project.render.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class AnimeShader extends BaseShader {
    public ShaderProgram outlineShader;
    public ShaderProgram characterShader;


    public AnimeShader() {
        outlineShader = new ShaderProgram(
            Gdx.files.internal("shaders/outline.vert"),
            Gdx.files.internal("shaders/outline.frag")
        );
        if (!outlineShader.isCompiled()) {
            throw new GdxRuntimeException("Outline Shader Error: " + outlineShader.getLog());
        }

        // Initialize character shader
        characterShader = new ShaderProgram(
            Gdx.files.internal("shaders/character.vert"),
            Gdx.files.internal("shaders/character.frag")
        );
        if (!characterShader.isCompiled()) {
            throw new GdxRuntimeException("Character Shader Error: " + characterShader.getLog());
        }

        this.program = outlineShader;
    }

    @Override
    public void init() {}

    @Override
    public void begin(Camera camera, RenderContext context) {
        // Called once per frame before rendering
        super.begin(camera, context);
        context.setDepthTest(1);
    }

    @Override
    public void render(Renderable renderable) {
        context.setDepthTest(GL20.GL_LEQUAL);
        context.setDepthMask(true);

        this.renderOutline(renderable);
        this.renderCharacter(renderable);
    }

    @Override
    public int compareTo(Shader other) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable renderable) {
        return true;
    }


    private void renderOutline(Renderable renderable) {
        outlineShader.bind();

        if (renderable.bones != null) {
            // Convert Matrix4[] to float[]
            float[] boneData = new float[16 * renderable.bones.length];
            for (int i = 0; i < renderable.bones.length; i++) {
                System.arraycopy(
                    renderable.bones[i].val, // Source: Matrix4's internal float[16]
                    0,                        // Source offset
                    boneData,                 // Destination array
                    i * 16,                   // Destination offset (each matrix is 16 floats)
                    16                        // Number of floats to copy
                );
            }

            // Pass boneData as the float array
            outlineShader.setUniformMatrix4fv("u_bones", boneData, 0, boneData.length);
        }

        outlineShader.setUniformMatrix("u_projViewTrans", camera.combined);
        outlineShader.setUniformMatrix("u_worldTrans", renderable.worldTransform);

        Matrix4 modelView = new Matrix4(camera.view).mul(renderable.worldTransform);
        Matrix3 normalMatrix = new Matrix3().set(modelView);
        normalMatrix = normalMatrix.inv().transpose();

        // 4. Pass it to the shader
        outlineShader.setUniformMatrix("u_normalMatrix", normalMatrix);
        outlineShader.setUniformf("u_outlineThickness", 0.006f);

        Vector4 outlineColor = new Vector4(0.0f, 0.0f, 0.0f, 1.0f);

        outlineShader.setUniformf("u_outlineColor", outlineColor);


        context.setCullFace(GL20.GL_FRONT);
        renderable.meshPart.render(outlineShader);
        context.setCullFace(GL20.GL_BACK);
    }

    private void renderCharacter(Renderable renderable) {
        characterShader.bind();

        // Pass bone matrices (same as outline)
        if (renderable.bones != null) {
            float[] boneData = new float[16 * renderable.bones.length];
            for (int i = 0; i < renderable.bones.length; i++) {
                System.arraycopy(
                    renderable.bones[i].val, 0,
                    boneData, i * 16, 16
                );
            }
            characterShader.setUniformMatrix4fv("u_bones", boneData, 0, boneData.length);
        }

        // Inside renderOutline() or renderCharacter():
        // 1. Compute the model-view matrix (world transform * camera view)
        Matrix4 modelView = new Matrix4(camera.view).mul(renderable.worldTransform);
        Matrix3 normalMatrix = new Matrix3().set(modelView);


        // 3. Invert and transpose the 3x3 matrix
        normalMatrix = normalMatrix.inv().transpose();

        // 4. Pass it to the shader
        characterShader.setUniformMatrix("u_normalMatrix", normalMatrix);


        // Pass character shader uniforms
        Vector3 lightDir = new Vector3(0.6f, 0.6f, 0.8f).nor();

        // Transform lightDir to world space
        Matrix4 invView = new Matrix4(camera.view).inv(); // Inverse of view matrix
        lightDir.rot(invView);
//        characterShader.setUniformf("u_lightColor", 1.0f, 1.0f, 1.0f); // White light
//        characterShader.setUniformf("u_shadowColor", 0.4f, 0.4f, 0.4f); // Dark gray shadows
//        characterShader.setUniformf("u_rimColor", 0.8f, 0.8f, 1.0f); // Bluish rim
//        characterShader.setUniformf("u_rimPower", 2.0f);

        Texture shadowRampTexture = new Texture(Gdx.files.internal("shadow_ramp_texture.png"));
        shadowRampTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        TextureAttribute texture = (TextureAttribute) renderable.material.get(TextureAttribute.Diffuse);
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        texture.textureDescription.texture.bind();
        characterShader.setUniformi("u_diffuseTexture", 0);

         // For hard transitions
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE1);
        shadowRampTexture.bind(1);
        characterShader.setUniformi("u_shadowRamp", 1);

        characterShader.setUniformMatrix("u_projViewTrans", camera.combined);
        characterShader.setUniformMatrix("u_worldTrans", renderable.worldTransform);
        characterShader.setUniformf("u_lightDir", 0.6f, 0.6f, 0.5f);

        // Render
        renderable.meshPart.render(characterShader);
    }

    public Texture createToonRampTexture() {
        // Create a Pixmap with width=4 and height=1.
        Pixmap pixmap = new Pixmap(4, 1, Pixmap.Format.RGBA8888);

        // Define discrete grayscale levels (or colors) for your ramp.
        // For a grayscale ramp, you might choose:
        // Dark: 30, 30, 30; Mid-dark: 85, 85, 85; Mid-bright: 170, 170, 170; Bright: 255, 255, 255.
        Color[] rampColors = new Color[]{
            new Color(30 / 255f, 30 / 255f, 30 / 255f, 1f),
            new Color(85 / 255f, 85 / 255f, 85 / 255f, 1f),
            new Color(170 / 255f, 170 / 255f, 170 / 255f, 1f),
            new Color(1f, 1f, 1f, 1f) // 255/255
        };

        // Fill the pixmap: each pixel in the 4x1 pixmap gets one ramp level.
        for (int x = 0; x < 4; x++) {
            // Set the color from the array:
            pixmap.setColor(rampColors[x]);
            // Fill a single pixel:
            pixmap.drawPixel(x, 0);
        }

        // Create a texture from the pixmap.
        Texture toonRampTexture = new Texture(pixmap);

        // Since we want hard transitions, set the filter mode to Nearest.
        toonRampTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Dispose the pixmap as we no longer need it after creating the texture.
        pixmap.dispose();

        return toonRampTexture;
    }

    @Override
    public void dispose() {
        if (outlineShader != null) outlineShader.dispose();
        if (characterShader != null) characterShader.dispose();
    }
}
