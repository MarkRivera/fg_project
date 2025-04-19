package io.github.fg_project.combat.kafu.state.simple;

import com.badlogic.gdx.Gdx;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.events.EventMarker;
import io.github.fg_project.components.hsm.FighterState;


public class KafuIdleState extends FighterState {
    public KafuIdleState(Fighter fighter) {
        super(fighter);
    }


    @Override
    public void enter() {
        System.out.println("Hello from Kafu's Idle State!");
        fighter.renderingComponent.fighterScene.animationController.setAnimation("Armature|mixamo.com|Layer0" , -1);
    }
    @Override
    public void exit() {
        System.out.println("Leaving from Kafu's Idle State!");
    }

    @Override
    public void update() {
//        Gdx.app.log("KAFU IDLE STATE", "This updated on a frame!");
    }

    @Override
    public boolean handleEvent(EventMarker event) {
        Gdx.app.log("KAFU IDLE STATE", "I should handle relevant events!" + event);
        return false;
    }
}
