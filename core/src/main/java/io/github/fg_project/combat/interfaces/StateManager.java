package io.github.fg_project.combat.interfaces;


import io.github.fg_project.combat.Fighter;
import io.github.fg_project.engine.math.FixedPoint;

public abstract class StateManager {
    protected BaseState currentState;

    public abstract void handleInput();
    public abstract void start(BaseState state);
    public abstract void update();
    public abstract void render();
    public abstract void transitionState(BaseState newState);
}
