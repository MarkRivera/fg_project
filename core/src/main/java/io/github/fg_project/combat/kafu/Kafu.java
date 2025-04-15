package io.github.fg_project.combat.kafu;

import com.badlogic.gdx.math.Vector3;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.kafu.state.KafuStateManager;
import io.github.fg_project.components.HealthComponent;
import io.github.fg_project.components.ManaComponent;
import io.github.fg_project.components.PhysicsComponent;
import io.github.fg_project.components.RenderingComponent;
import io.github.fg_project.engine.math.FixedPoint;
import io.github.fg_project.engine.math.Vec3fp;
import io.github.fg_project.render.FighterAssetLoader;

public class Kafu extends Fighter {
    KafuStateManager kafuStateManager;
    public Kafu(Vec3fp initialPosition) {
        // create fighter scene
        super(
            new PhysicsComponent(initialPosition),
            new HealthComponent(1000, 1000),
            new ManaComponent(1000, 1000),
            new RenderingComponent("models/fighters/tifa/source/kachu.glb")
        );

        // Make this a component later
        this.kafuStateManager = new KafuStateManager();
    }

    @Override
    public void start() {
        this.kafuStateManager.start(this.renderingComponent.fighterScene.animationController);
    }

    @Override
    public void update(FixedPoint deltaTime) {
        this.kafuStateManager.update();
    }
    @Override
    public void renderFrame() {

    }
    @Override
    public void updateCollisionBounds() {

    }
    @Override
    public void changeState() {

    }
}
