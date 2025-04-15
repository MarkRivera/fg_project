package io.github.fg_project.combat.kafu.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KafuIdleState extends KafuBaseState {
    @Override
    public void onEnter(KafuStateManager kafuContext) {
        System.out.println("Hello from Kafu's Idle State!");
        kafuContext.animationController.setAnimation("Armature|mixamo.com|Layer0" , -1);
    }
    @Override
    public void updateState(KafuStateManager kafuContext) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            kafuContext.switchState(kafuContext.jumpState);
        }
    }
    @Override
    public void onExit(KafuStateManager kafuContext) {
        System.out.println("Leaving from Kafu's Idle State!");
    }

    @Override
    public void onCollisionEnter(KafuStateManager kafuContext) {}
}
