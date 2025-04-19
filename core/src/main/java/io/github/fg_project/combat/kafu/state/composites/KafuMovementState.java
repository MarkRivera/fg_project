package io.github.fg_project.combat.kafu.state.composites;

import com.badlogic.gdx.Gdx;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.kafu.state.simple.KafuCrouchState;
import io.github.fg_project.combat.kafu.state.simple.KafuIdleState;
import io.github.fg_project.combat.kafu.state.simple.KafuJumpState;
import io.github.fg_project.components.hsm.CompositeState;

public class KafuMovementState extends CompositeState {
    public Fighter fighter;
    public KafuIdleState kafuIdleState;
    public KafuCrouchState kafuCrouchState;
    public KafuJumpState kafuJumpState;
    public KafuMovementState(Fighter fighter) {
        super();
        this.fighter = fighter;
        this.kafuIdleState =  new KafuIdleState(fighter);
        this.kafuCrouchState = new KafuCrouchState(fighter);
        this.kafuJumpState = new KafuJumpState(fighter);

        this.setInitialSubState(kafuIdleState);
    }

    @Override
    protected void onEnter() {
        Gdx.app.log("KAFU MOVEMENT STATE", "Entering movement tree");
    }

    @Override
    protected void onExit() {
        Gdx.app.log("KAFU MOVEMENT STATE", "Exiting movement tree");
    }
}
