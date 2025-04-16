package io.github.fg_project.combat.kafu.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KafuJumpState extends KafuBaseState {
    @Override
    public void onEnter(KafuStateManagerComponent ctx) {
        System.out.println("Hello from Kafu's Jump State!");
    }

    @Override
    public void updateState(KafuStateManagerComponent ctx) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            ctx.switchState(ctx.idleState);
        }
    }

    @Override
    public void onExit(KafuStateManagerComponent ctx) {
        System.out.println("Landing from Jump after 3 seconds, leaving jump state");
    }

    @Override
    public void onCollisionEnter(KafuStateManagerComponent ctx) {

    }
}
