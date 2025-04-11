package io.github.fg_project.render.lights;

import com.badlogic.gdx.graphics.Color;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;

public class DirectionalLightExBuilder {
    private DirectionalLightEx light;

    public DirectionalLightExBuilder() {
        this.light = new DirectionalLightEx();
    }

    public DirectionalLightExBuilder setDirection(float x, float y, float z) {
        if (this.light == null) {
            throw new IllegalStateException("Light should exist before setting direction");
        }

        this.light.direction.set(x, y, z);
        return this;
    }

    public DirectionalLightExBuilder normalize() {
        if (this.light == null) {
            throw new IllegalStateException("Light should exist before normalizing");
        }

        this.light.direction.nor();
        return this;
    }

    public DirectionalLightExBuilder setColor(Color color) {
        if (this.light == null) {
            throw new IllegalStateException("Light should exist before setting color");
        }

        this.light.color.set(color);
        return this;
    }

    public DirectionalLightEx build() {
        return this.light;
    }
}
