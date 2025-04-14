package io.github.fg_project.combat.kafu;

import com.badlogic.gdx.math.Vector3;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.engine.math.Vec3fp;
import io.github.fg_project.render.FighterAssetLoader;

public class Kafu extends Fighter {
    public Kafu(Vec3fp initialPosition) {
        super(initialPosition);

        // create fighter scene
        String fighterModelPath = "models/fighters/tifa/source/kachu.glb";
        loader = new FighterAssetLoader()
            .setAssetLocation(fighterModelPath)
            .load()
            .createScene();
        fighterScene = loader.build();
        fighterScene.modelInstance.transform.setToRotation(Vector3.Y, 90);
        fighterScene.animationController.setAnimation("Armature|mixamo.com|Layer0" , -1);
    }

    public Kafu(Vec3fp initialPosition, int startingHealth, int startingMp) {
        super(initialPosition, startingHealth, startingMp);

        // create fighter scene
        String fighterModelPath = "models/fighters/tifa/source/kachu.glb";
        loader = new FighterAssetLoader()
            .setAssetLocation(fighterModelPath)
            .load()
            .createScene();
        fighterScene = loader.build();
        fighterScene.modelInstance.transform.setToRotation(Vector3.Y, 90);
        fighterScene.animationController.setAnimation("Armature|mixamo.com|Layer0" , -1);
    }
}
