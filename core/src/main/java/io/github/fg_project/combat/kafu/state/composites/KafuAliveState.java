package io.github.fg_project.combat.kafu.state.composites;

import com.badlogic.gdx.Gdx;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.events.CommonEvent;
import io.github.fg_project.combat.events.EventMarker;
import io.github.fg_project.components.hsm.CompositeState;

public class KafuAliveState extends CompositeState {
    public Fighter fighter;
    public CompositeState movementState;
    public CompositeState hitState;

    public KafuAliveState(Fighter fighter) {
        super();
        this.fighter = fighter;
        this.movementState = new KafuMovementState(fighter);
        this.hitState = new KafuHitState(fighter);

        this.setInitialSubState(this.movementState);
    }

    @Override
    protected void onEnter() {
        Gdx.app.log("KAFU ALIVE STATE", "Entered Alive State");
    }

    @Override
    protected void onExit() {
        Gdx.app.log("KAFU ALIVE STATE", "Exiting Alive State");
    }

    @Override
    protected boolean onEvent(EventMarker event) {
        if (event.equals(CommonEvent.HIT)) {
            subMachine.changeState(hitState);
            return true;
        } else if (event.equals(CommonEvent.RECOVER)) {
            subMachine.changeState(movementState);
            return true;
        } else if (event.equals(CommonEvent.DIE)) {
            return false;
        }
        Gdx.app.error("KAFU ALIVE STATE", "KAFU ALIVE STATE did not handle this event " + event);
        return false;
    }
}
