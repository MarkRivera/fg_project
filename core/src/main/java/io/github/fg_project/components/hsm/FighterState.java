package io.github.fg_project.components.hsm;

import io.github.fg_project.combat.Fighter;

public abstract class FighterState implements State {
    protected final Fighter fighter;
    public FighterState(Fighter fighter) {
        this.fighter = fighter;
    }
}
