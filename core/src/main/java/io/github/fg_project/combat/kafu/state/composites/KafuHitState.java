package io.github.fg_project.combat.kafu.state.composites;

import com.badlogic.gdx.Gdx;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.kafu.state.simple.KafuNormalHitState;
import io.github.fg_project.components.hsm.CompositeState;

public class KafuHitState extends CompositeState {
    public Fighter fighter;
    public KafuNormalHitState normalHitState;
    public KafuHitState(Fighter fighter) {
        super();
        this.fighter = fighter;
        this.normalHitState = new KafuNormalHitState(fighter);
        this.setInitialSubState(normalHitState);
    }

    @Override
    protected void onEnter() {
        Gdx.app.log("KAFU HIT STATE", "Entering hit tree");
    }

    @Override
    protected void onExit() {
        Gdx.app.log("KAFU HIT STATE", "Exiting hit tree");
    }
}
