package io.github.fg_project.combat.kafu.state;

import com.badlogic.gdx.Gdx;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.events.CommonEvent;
import io.github.fg_project.combat.events.EventMarker;
import io.github.fg_project.combat.kafu.state.composites.KafuAliveState;
import io.github.fg_project.combat.kafu.state.composites.KafuDeadState;
import io.github.fg_project.components.hsm.CompositeState;
import io.github.fg_project.components.hsm.StateMachine;

public class KafuStateMachine extends StateMachine {
    public CompositeState aliveState;
    public CompositeState deadState;


    public KafuStateMachine(Fighter fighter) {
        this.aliveState = new KafuAliveState(fighter);
        this.deadState = new KafuDeadState(fighter);

        this.setOwner(fighter);
    }

    public void handleEvent(EventMarker event) {
        Gdx.app.log("KAFU STATE MACHINE", String.valueOf(this.current == null));
        if (this.current != null && current.handleEvent(event)) {
            return;
        }

        if (event.equals(CommonEvent.DIE)) {
            this.changeState(this.deadState);
        } else {
            Gdx.app.log("KAFU ROOT MACHINE", "Did not handle the event: " + event);
        }
    }
}
