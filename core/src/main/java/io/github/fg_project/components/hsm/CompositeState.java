package io.github.fg_project.components.hsm;

import io.github.fg_project.combat.events.EventMarker;

public class CompositeState implements State {
    protected StateMachine subMachine = new StateMachine();
    private State initialSubState;

    public CompositeState() {}

    protected void setInitialSubState(State initialSubState) {
        this.initialSubState = initialSubState;
    }

    @Override
    public void enter() {
        onEnter();
        subMachine.changeState(initialSubState);
    }

    @Override
    public void exit() {
        subMachine.changeState(null);
        onExit();
    }

    @Override
    public void update() {
        onUpdate();
        subMachine.update();
    }

    @Override
    public boolean handleEvent(EventMarker event) {
        subMachine.handleEvent(event);

        return onEvent(event);
    }

    protected void onEnter() {}
    protected void onExit() {}
    protected void onUpdate() {}
    protected boolean onEvent(EventMarker event) { return false; }
}
