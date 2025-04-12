package io.github.fg_project.render.shaders.providers;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import io.github.fg_project.render.shaders.AnimeShader;

public class AnimeShaderProvider extends DefaultShaderProvider {
    private final AnimeShader shader;

    public AnimeShaderProvider() {
        shader = new AnimeShader();
    }

    @Override
    protected Shader createShader(Renderable renderable) {
        return shader;
    }
}
