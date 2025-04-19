package io.github.fg_project.components.hsm;

import io.github.fg_project.combat.events.EventMarker;

public interface State {
    void enter();
    void exit();
    void update();
    boolean handleEvent(EventMarker event);
}
