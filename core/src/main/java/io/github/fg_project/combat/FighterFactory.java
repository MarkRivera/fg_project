package io.github.fg_project.combat;

import io.github.fg_project.combat.commands.InputComponent;
import io.github.fg_project.combat.kafu.Kafu;
import io.github.fg_project.combat.kafu.state.KafuStateMachine;
import io.github.fg_project.components.HealthComponent;
import io.github.fg_project.components.ManaComponent;
import io.github.fg_project.components.PhysicsComponent;
import io.github.fg_project.components.RenderingComponent;
import io.github.fg_project.engine.math.Vec3fp;

public class FighterFactory {
    public static Fighter createKafu(Vec3fp initialPosition) {
        // # Components
        HealthComponent healthComponent = new HealthComponent(1000, 1000);
        ManaComponent manaComponent = new ManaComponent(1000,1000);
        PhysicsComponent physicsComponent = new PhysicsComponent(initialPosition);
        RenderingComponent renderingComponent = new RenderingComponent("models/fighters/tifa/source/kachu.glb");
        InputComponent inputComponent = new InputComponent();

        Fighter kafu = new Kafu(physicsComponent, healthComponent, manaComponent, renderingComponent, inputComponent);
        KafuStateMachine kafuStateMachine = new KafuStateMachine(kafu);
        kafu.setStateMachine(kafuStateMachine);

        return kafu;
    }
}
