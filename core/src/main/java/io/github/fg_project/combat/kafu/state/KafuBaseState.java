package io.github.fg_project.combat.kafu.state;

import io.github.fg_project.combat.kafu.state.interfaces.BaseState;

public abstract class KafuBaseState {
    public abstract void onEnter(KafuStateManager kafuContext);
    public abstract void updateState(KafuStateManager kafuContext);
    public abstract void onExit(KafuStateManager kafuContext);
    public abstract void onCollisionEnter(KafuStateManager kafuContext);
}
