package io.github.fg_project.combat.kafu;

import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.commands.InputComponent;
import io.github.fg_project.combat.kafu.state.KafuStateManagerComponent;
import io.github.fg_project.components.HealthComponent;
import io.github.fg_project.components.ManaComponent;
import io.github.fg_project.components.PhysicsComponent;
import io.github.fg_project.components.RenderingComponent;
import io.github.fg_project.engine.math.FixedPoint;
import io.github.fg_project.engine.math.Vec3fp;


public class Kafu extends Fighter {
    public Kafu(Vec3fp initialPosition) {
        super(
            new PhysicsComponent(initialPosition),
            new HealthComponent(1000, 1000),
            new ManaComponent(1000, 1000),
            new RenderingComponent("models/fighters/tifa/source/kachu.glb"),
            new InputComponent(),
            new KafuStateManagerComponent()
        );
    }


    @Override
    public void start() {
        this.setupInstances(this.stateManagerComponent);
        this.stateManagerComponent.start();
    }

    @Override
    public void update(FixedPoint deltaTime) {
        this.stateManagerComponent.update();
    }
    @Override
    public void render() {

    }
    @Override
    public void updateCollisionBounds() {

    }
    @Override
    public void changeState() {

    }
}
