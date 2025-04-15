package io.github.fg_project.combat.kafu.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.Timer;
import java.util.TimerTask;

public class KafuJumpState extends KafuBaseState {
    @Override
    public void onEnter(KafuStateManager kafuContext) {
        System.out.println("Hello from Kafu's Jump State!");
    }
    @Override
    public void updateState(KafuStateManager kafuContext) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            kafuContext.switchState(kafuContext.idleState);
        }
    }
    @Override
    public void onExit(KafuStateManager kafuContext) {
        System.out.println("Landing from Jump after 3 seconds, leaving jump state");
    }

    @Override
    public void onCollisionEnter(KafuStateManager kafuContext) {}
}
