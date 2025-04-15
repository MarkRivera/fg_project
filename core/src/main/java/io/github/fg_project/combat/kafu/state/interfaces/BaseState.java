package io.github.fg_project.combat.kafu.state.interfaces;

public interface BaseState {
    public void onEnter(StateManager context);
    public void onExit(StateManager context);
    public void updateState(StateManager context);
    public void onCollisionEnter(StateManager context);
}
