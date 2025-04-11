package io.github.fg_project.render.shaders;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;

public class DebugShader extends DefaultShaderProvider {
    DefaultShader.Config config;
    public DebugShader() {
        // Create a shader configuration with an increased maxBones value.
        config = new DefaultShader.Config();
        config.numBones = 128; // Set this to a value >= your model's bone count (75 in your case)

    }

    @Override
    protected DefaultShader createShader(Renderable renderable) {
        // Every shader created using this provider will now use the custom configuration.
        return new DefaultShader(renderable, this.config);
    }
}
