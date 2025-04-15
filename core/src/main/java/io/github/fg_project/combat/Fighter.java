package io.github.fg_project.combat;


// A fighter is made out of health, mp, hitboxes, hurtboxes, state, 3d meshes, collision boxes, push boxes, throw boxes
// position, velocity, acceleration, audio files, materials and shaders

import io.github.fg_project.components.HealthComponent;
import io.github.fg_project.components.ManaComponent;
import io.github.fg_project.components.PhysicsComponent;
import io.github.fg_project.components.RenderingComponent;
import io.github.fg_project.engine.math.FixedPoint;
import io.github.fg_project.engine.math.Vec3fp;
import io.github.fg_project.render.FighterAssetLoader;
import net.mgsx.gltf.scene3d.scene.Scene;

public abstract class Fighter {
    public RenderingComponent renderingComponent;
    public PhysicsComponent physicsComponent;
    public HealthComponent healthComponent;
    public ManaComponent manaComponent;

    public boolean onGround;
    // Collision
    // Visuals
    // Audio

    public Fighter(PhysicsComponent physicsComponent, HealthComponent healthComponent, ManaComponent manaComponent, RenderingComponent renderingComponent) {
        this.physicsComponent = physicsComponent;
        this.healthComponent = healthComponent;
        this.manaComponent = manaComponent;
        this.renderingComponent = renderingComponent;

        this.onGround = true;
    }

    public abstract void start();
    public abstract void update(FixedPoint deltaTime);
    public abstract void renderFrame();
    public abstract void updateCollisionBounds();
    public abstract void changeState();
}
