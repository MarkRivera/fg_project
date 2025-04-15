package io.github.fg_project.combat.kafu.state.interfaces;

public interface StateManager {
    public void start();
    public void update();
    public void switchState(BaseState state);
}
