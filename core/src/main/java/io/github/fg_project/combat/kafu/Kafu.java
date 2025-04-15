package io.github.fg_project.combat.kafu;

import com.badlogic.gdx.math.Vector3;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.kafu.state.KafuStateManager;
import io.github.fg_project.engine.math.FixedPoint;
import io.github.fg_project.engine.math.Vec3fp;
import io.github.fg_project.render.FighterAssetLoader;

public class Kafu extends Fighter {
    KafuStateManager kafuStateManager;
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

        this.kafuStateManager = new KafuStateManager();
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

        this.kafuStateManager = new KafuStateManager();
    }

    @Override
    public void start() {
        this.kafuStateManager.start(this.fighterScene.animationController);
    }

    @Override
    public void update(FixedPoint deltaTime) {
        this.kafuStateManager.update();
    }
    @Override
    public void renderFrame() {

    }
    @Override
    public void updateCollisionBounds() {

    }
    @Override
    public void changeState() {

    }
}
