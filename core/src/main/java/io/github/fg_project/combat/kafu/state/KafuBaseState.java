package io.github.fg_project.combat.kafu.state;

import io.github.fg_project.combat.Fighter;

public abstract class KafuBaseState {
    protected Fighter core;

    public abstract void handleInput();
    public abstract void onEnter(KafuStateManagerComponent ctx);
    public abstract void updateState(KafuStateManagerComponent ctx);
    public abstract void onExit(KafuStateManagerComponent ctx);
    public abstract void onCollisionEnter(KafuStateManagerComponent ctx);

    public void setCore(Fighter core) {
        this.core = core;
    }
}
