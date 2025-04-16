package io.github.fg_project.combat.state;

import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.kafu.state.KafuStateManagerComponent;


public abstract class BaseState {
    protected Fighter core;

    public abstract void onEnter(KafuStateManagerComponent context);
    public abstract void updateState(KafuStateManagerComponent context);
    public abstract void onExit(KafuStateManagerComponent context);
    public abstract void onCollisionEnter(KafuStateManagerComponent context);

    public void setCore(Fighter core) {
        this.core = core;
    }
}
