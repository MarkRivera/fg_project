package io.github.fg_project.combat.kafu.state;

import io.github.fg_project.combat.interfaces.Command;

public class KafuIdleState extends KafuBaseState {
    @Override
    public void handleInput() {

    }

    @Override
    public void onEnter(KafuStateManagerComponent ctx) {
        System.out.println("Hello from Kafu's Idle State!");
        this.core.renderingComponent.fighterScene.animationController.setAnimation("Armature|mixamo.com|Layer0" , -1);
    }

    @Override
    public void updateState(KafuStateManagerComponent ctx) {
        Command command = this.core.inputComponent.handleInput();
        if (command != null) {
            ctx.switchState(ctx.jumpState);
        }
    }

    @Override
    public void onExit(KafuStateManagerComponent ctx) {
        System.out.println("Leaving from Kafu's Idle State!");
    }

    @Override
    public void onCollisionEnter(KafuStateManagerComponent ctx) {

    }
}
