package io.github.fg_project.combat.interfaces;

import io.github.fg_project.combat.state.BaseState;

public interface StateManager {
    public abstract void start();
    public abstract void update();
    public abstract void switchState(BaseState state);
}
