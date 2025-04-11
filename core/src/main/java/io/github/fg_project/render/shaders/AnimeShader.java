package io.github.fg_project.render.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;

public class AnimeShader extends BaseShader {
    private int u_projViewTrans;
    private int u_worldTrans;
    private int u_outlineThickness;
    public ShaderProgram outlineShader;
    public ShaderProgram characterShader;

    public AnimeShader(Renderable renderable) {
        outlineShader = new ShaderProgram(
            Gdx.files.internal("shaders/outline.vert"),
            Gdx.files.internal("shaders/outline.frag"));

//        register()

        characterShader = new ShaderProgram(
            Gdx.files.internal("shaders/character.vert"),
            Gdx.files.internal("shaders/character.frag"));

        // Check for errors
        if (!outlineShader.isCompiled()) throw new GdxRuntimeException(outlineShader.getLog());
        if (!characterShader.isCompiled()) throw new GdxRuntimeException(characterShader.getLog());
    }

    @Override
    public void init() {
//        context = new RenderContext();
    }

    @Override
    public int compareTo(Shader other) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable renderable) {
        return false;
    }

//    public void begin(Camera camera, Environment environment) {
//        context.begin();
//
//        // First render outline
//        renderOutline(camera);
//
//        // Then setup character shader
//        characterShader.begin();
//        characterShader.setUniformMatrix("u_projViewTrans", camera.combined);
//
//        // Set lighting uniforms from environment
//        if(environment != null) {
//            characterShader.setUniformf("u_lightDir", environment.get(0).direction);
//            characterShader.setUniformf("u_lightColor", environment.getDirectionalLight(0).color);
//        }
//    }

    private void renderOutline(ModelInstance instance, Camera camera) {
        outlineShader.bind();
        outlineShader.setUniformMatrix("u_projViewTrans", camera.combined);
        outlineShader.setUniformMatrix("u_worldTrans", instance.transform);
        outlineShader.setUniformf("u_outlineThickness", 0.015f);
        outlineShader.setUniformf("u_outlineColor", Color.BLACK);
    }

    private void renderCharacter(ModelInstance instance, Environment env, Camera camera) {
//        characterShader.begin();
//        characterShader.setUniformMatrix("u_projViewTrans", camera.combined);
//        characterShader.setUniformMatrix("u_worldTrans", instance.transform);
//        characterShader.setUniformMatrix("u_normalMatrix", instance.transform.toNormalMatrix());
//
//        // Set lighting uniforms
//        characterShader.setUniformf("u_lightDir", env.directionalLight.direction);
//        characterShader.setUniformf("u_lightColor", env.directionalLight.color);
//        characterShader.setUniformf("u_shadowColor", new Color(0.3f, 0.3f, 0.4f, 1f));
//        characterShader.setUniformf("u_rimColor", new Color(0.8f, 0.8f, 1f, 1f));
//
//        // Bind textures
//        diffuseTexture.bind(0);
//        characterShader.setUniformi("u_diffuseTexture", 0);
//
//        instance.model.render(instance, characterShader);
//        characterShader.end();
    }
}
