package io.github.fg_project.combat.kafu.state.simple;

import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.events.EventMarker;
import io.github.fg_project.components.hsm.FighterState;

public class KafuJumpState extends FighterState {
    public KafuJumpState(Fighter fighter) {
        super(fighter);
    }

    @Override
    public void enter() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update() {

    }

    @Override
    public boolean handleEvent(EventMarker event) {
        return false;
    }
}
