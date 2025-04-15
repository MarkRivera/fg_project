package io.github.fg_project.combat.kafu.state;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import io.github.fg_project.combat.kafu.state.interfaces.StateManager;

public class KafuStateManager {
    // See if there is a class I can inherit that has these functions
    public AnimationController animationController;
    public KafuBaseState currentState;

    public KafuIdleState idleState = new KafuIdleState();
    public KafuCrouchState crouchState = new KafuCrouchState();
    public KafuJumpState jumpState = new KafuJumpState();


    // Start is called before the first update
    public void start(AnimationController animationController) {
        this.animationController = animationController;
        this.currentState = this.idleState; // By default, start here
        this.currentState.onEnter(this); // Call the onEnter function within the current state
    }

    // Update is called once per frame
    public void update() {
        this.currentState.updateState(this);
    }

    public void switchState(KafuBaseState state) {
        this.currentState.onExit(this);
        this.currentState = state;
        this.currentState.onEnter(this);
    }
}
