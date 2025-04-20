package io.github.fg_project.combat;


// A fighter is made out of health, mp, hitboxes, hurtboxes, state, 3d meshes, collision boxes, push boxes, throw boxes
// position, velocity, acceleration, audio files, materials and shaders

import io.github.fg_project.combat.commands.InputComponent;
import io.github.fg_project.components.HealthComponent;
import io.github.fg_project.components.ManaComponent;
import io.github.fg_project.components.PhysicsComponent;
import io.github.fg_project.components.RenderingComponent;
import io.github.fg_project.components.hsm.StateMachine;
import io.github.fg_project.input.actions.Direction;


public abstract class Fighter {
    public RenderingComponent renderingComponent;
    public PhysicsComponent physicsComponent;
    public HealthComponent healthComponent;
    public ManaComponent manaComponent;
    public InputComponent inputComponent;
    private StateMachine stateMachine;

    // Collision
    // Audio
    public boolean onGround;
    private Direction facingDirection;

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
        this.facingDirection = Direction.RIGHT;
    }

    public StateMachine getStateMachine() {
        return this.stateMachine;
    }

    public void setStateMachine(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public abstract void start();
    public abstract void handleInput();
    public abstract void update();
    public abstract void render();
}
