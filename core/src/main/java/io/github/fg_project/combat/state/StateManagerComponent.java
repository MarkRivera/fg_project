package io.github.fg_project.combat.state;

public interface StateManagerComponent<T> {
    public abstract void start();
    public abstract void update();
    public abstract void switchState(T state);
}
