package io.github.fg_project.components;

import com.badlogic.gdx.math.Vector3;
import io.github.fg_project.render.FighterAssetLoader;
import net.mgsx.gltf.scene3d.scene.Scene;

public class RenderingComponent {
    // Assets
    public FighterAssetLoader loader;
    public Scene fighterScene;

    public RenderingComponent(String path) {
        loader = new FighterAssetLoader()
            .setAssetLocation(path)
            .load()
            .createScene();
        fighterScene = loader.build();
        fighterScene.modelInstance.transform.setToRotation(Vector3.Y, 90);
    }
}
