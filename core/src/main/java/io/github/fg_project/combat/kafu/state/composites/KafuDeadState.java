package io.github.fg_project.combat.kafu.state.composites;

import com.badlogic.gdx.Gdx;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.events.EventMarker;
import io.github.fg_project.components.hsm.CompositeState;

public class KafuDeadState extends CompositeState {
    public Fighter fighter;
    public KafuDeadState(Fighter fighter) {
        super();
        this.fighter = fighter;
    }

    @Override
    public void enter() {
        Gdx.app.log("KAFU DEAD STATE", "Entered Dead State");
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
