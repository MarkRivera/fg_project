package io.github.fg_project.components.hsm;

import com.badlogic.gdx.Gdx;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.events.EventMarker;

public class StateMachine {
    protected Fighter owner;
    protected State current;

    public Fighter getOwner() {
        return this.owner;
    }
    public void setOwner(Fighter fighter) {
        this.owner = fighter;
    }
    public void changeState(State to) {
        if (current != null) current.exit();
        current = to;
        current.enter();
    }

    public void update() {
        if (this.current != null) this.current.update();
    }

    public void handleEvent(EventMarker eventMarker) {
        if (current != null && current.handleEvent(eventMarker)) {
            // event consumed
        } else {
            // (optional) handle unconsumed events at machine level
        }
    }
}
