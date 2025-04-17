package io.github.fg_project.combat.kafu;

import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.commands.InputComponent;
import io.github.fg_project.combat.interfaces.BaseState;
import io.github.fg_project.components.HealthComponent;
import io.github.fg_project.components.ManaComponent;
import io.github.fg_project.components.PhysicsComponent;
import io.github.fg_project.components.RenderingComponent;
import io.github.fg_project.engine.math.Vec3fp;


public class Kafu extends Fighter {
    public Kafu(Vec3fp initialPosition) {
        super(
            new PhysicsComponent(initialPosition),
            new HealthComponent(1000, 1000),
            new ManaComponent(1000, 1000),
            new RenderingComponent("models/fighters/tifa/source/kachu.glb"),
            new InputComponent()
        );
    }

    @Override
    public void handleInput() {
        currentState.handleInput(this);
    }

    @Override
    public void start(BaseState state) {
        currentState = state;
        currentState.onEnter(this);
    }

    @Override
    public void update() {
        currentState.update(this);
    }

    @Override
    public void render() {
        currentState.render(this);
    }

    @Override
    public void transitionState(BaseState newState) {
        currentState.onExit(this);
        currentState = newState;
        currentState.onEnter(this);
    }
}
