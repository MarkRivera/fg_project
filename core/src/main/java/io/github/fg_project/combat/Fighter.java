package io.github.fg_project.combat;


// A fighter is made out of health, mp, hitboxes, hurtboxes, state, 3d meshes, collision boxes, push boxes, throw boxes
// position, velocity, acceleration, audio files, materials and shaders

import io.github.fg_project.combat.commands.InputComponent;
import io.github.fg_project.combat.interfaces.StateManager;
import io.github.fg_project.components.HealthComponent;
import io.github.fg_project.components.ManaComponent;
import io.github.fg_project.components.PhysicsComponent;
import io.github.fg_project.components.RenderingComponent;


public abstract class Fighter extends StateManager {
    public RenderingComponent renderingComponent;
    public PhysicsComponent physicsComponent;
    public HealthComponent healthComponent;
    public ManaComponent manaComponent;
    public InputComponent inputComponent;

    // Collision
    // Audio
    public boolean onGround;

    public Fighter(PhysicsComponent physicsComponent,
                   HealthComponent healthComponent,
                   ManaComponent manaComponent,
                   RenderingComponent renderingComponent,
                   InputComponent inputComponent) {
        this.physicsComponent = physicsComponent;
        this.healthComponent = healthComponent;
        this.manaComponent = manaComponent;
        this.renderingComponent = renderingComponent;
        this.inputComponent = inputComponent;

        this.onGround = true;
    }
}
