package io.github.fg_project.combat.kafu;

import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.commands.InputComponent;
import io.github.fg_project.combat.kafu.state.KafuStateMachine;
import io.github.fg_project.components.HealthComponent;
import io.github.fg_project.components.ManaComponent;
import io.github.fg_project.components.PhysicsComponent;
import io.github.fg_project.components.RenderingComponent;
import io.github.fg_project.components.hsm.StateMachine;
import io.github.fg_project.engine.math.Vec3fp;


public class Kafu extends Fighter {
    public Kafu(PhysicsComponent physicsComponent, HealthComponent healthComponent, ManaComponent manaComponent,
                RenderingComponent renderingComponent, InputComponent inputComponent) {
        super(physicsComponent, healthComponent, manaComponent, renderingComponent, inputComponent);
    }

    @Override
    public void start() {
        KafuStateMachine root = (KafuStateMachine) this.getStateMachine();
        root.changeState(root.aliveState);
    }
    @Override
    public void handleInput() {
//        this.inputComponent.handleInput();
    }
    @Override
    public void update() {
        this.getStateMachine().update();
    }
    @Override
    public void render() {}
}
