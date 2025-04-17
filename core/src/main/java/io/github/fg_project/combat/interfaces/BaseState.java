package io.github.fg_project.combat.interfaces;

import io.github.fg_project.combat.Fighter;

public abstract class BaseState {
    public abstract void handleInput(Fighter fighter);
    public abstract void update(Fighter fighter);
    public abstract void onEnter(Fighter fighter);
    public abstract void onExit(Fighter fighter);
    public abstract void render(Fighter fighter);
}
