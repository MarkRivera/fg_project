package io.github.fg_project.render.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class AnimeShader extends BaseShader {
    public ShaderProgram outlineShader;
    public ShaderProgram characterShader;
    private Renderable renderable;
    Matrix4 normalMatrix = new Matrix4();


    public AnimeShader() {

        outlineShader = new ShaderProgram(
            Gdx.files.internal("shaders/outline.vert"),
            Gdx.files.internal("shaders/outline.frag"));
        this.program = outlineShader;
        // Check for errors
        if (!outlineShader.isCompiled()) throw new GdxRuntimeException(outlineShader.getLog());
//        if (!characterShader.isCompiled()) throw new GdxRuntimeException(characterShader.getLog());
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
        this.renderOutline(renderable);
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
        outlineShader.setUniformMatrix("u_projViewTrans", camera.combined);
        outlineShader.setUniformMatrix("u_worldTrans", renderable.worldTransform);

        normalMatrix.set(camera.view).mul(renderable.worldTransform).inv().tra();
        outlineShader.setUniformMatrix("u_normalMatrix", normalMatrix);
        outlineShader.setUniformf("u_outlineThickness", 1f);

        renderable.meshPart.render(outlineShader);
    }

    private void renderCharacter(ModelInstance instance, Environment env, Camera camera) {
        characterShader.bind();
        characterShader.setUniformMatrix("u_projViewTrans", camera.combined);
        characterShader.setUniformMatrix("u_worldTrans", renderable.worldTransform);

        // Normal matrix (same as outline)
        characterShader.setUniformMatrix("u_normalMatrix", normalMatrix);

        // Set other uniforms (lighting, colors, etc.)
        characterShader.setUniformf("u_lightDir", 0.6f, 0.6f, 0.8f);
        characterShader.setUniformf("u_baseColor", 0.8f, 0.6f, 0.4f);

        // Render the main model
        renderable.meshPart.render(characterShader);
    }

    @Override
    public void dispose() {
        if (outlineShader != null) outlineShader.dispose();
        if (characterShader != null) characterShader.dispose();
    }
}
