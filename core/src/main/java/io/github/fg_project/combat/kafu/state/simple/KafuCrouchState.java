package io.github.fg_project.combat.kafu.state.simple;

import com.badlogic.gdx.Gdx;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.events.EventMarker;
import io.github.fg_project.components.hsm.FighterState;


public class KafuCrouchState extends FighterState {
    public KafuCrouchState(Fighter fighter) {
        super(fighter);
    }

    @Override
    public void enter() {
        Gdx.app.log("CROUCH STATE", "Entering Kafu Crouch State");
    }

    @Override
    public void exit() {
        Gdx.app.log("CROUCH STATE", "Exiting Kafu Crouch State");
    }

    @Override
    public void update() {

    }

    @Override
    public boolean handleEvent(EventMarker event) {
        return false;
    }
}
