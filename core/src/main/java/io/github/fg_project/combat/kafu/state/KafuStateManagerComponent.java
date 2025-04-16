package io.github.fg_project.combat.kafu.state;

public class KafuStateManagerComponent {
    public KafuBaseState currentState;
    public KafuBaseState idleState = new KafuIdleState();
    public KafuBaseState crouchState = new KafuCrouchState();
    public KafuBaseState jumpState = new KafuJumpState();

    public void start() {
        this.currentState = this.idleState; // By default, start here
        this.currentState.onEnter(this); // Call the onEnter function within the current state
    }

    public void update() {
        this.currentState.updateState(this);
    }

    public void switchState(KafuBaseState state) {
        this.currentState.onExit(this);
        this.currentState = state;
        this.currentState.onEnter(this);
    }
}
