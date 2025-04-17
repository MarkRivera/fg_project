package io.github.fg_project.combat.kafu.state;

import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.interfaces.BaseState;

public class KafuCrouchState extends BaseState {
    @Override
    public void handleInput(Fighter fighter) {}
    @Override
    public void onEnter(Fighter fighter) {
        System.out.println("Hello from Kafu's Crouch State!");
//        this.core.renderingComponent.fighterScene.animationController.setAnimation("Armature|mixamo.com|Layer0" , -1);
    }
    @Override
    public void update(Fighter fighter) {}
    @Override
    public void render(Fighter fighter) {}
    @Override
    public void onExit(Fighter fighter) {
        System.out.println("Leaving from Kafu's Crouch State!");
    }
}
