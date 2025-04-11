package io.github.fg_project.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class FighterAssetLoader implements Disposable {
    public String assetLocation;
    public GLBLoader loader;
    public SceneAsset asset; // Contains models, materials and animations, etc...
    public Scene scene;

    public FighterAssetLoader() {
        this.loader = new GLBLoader();
    }

    public FighterAssetLoader setAssetLocation(String assetLocation) {
        this.assetLocation = assetLocation;
        return this;
    }

    public FighterAssetLoader load() {
        if (this.assetLocation == null || this.assetLocation.isEmpty()) {
            throw new IllegalArgumentException("Asset location must be provided.");
        }

        this.asset = this.loader.load(Gdx.files.internal(assetLocation));
        return this;
    }

    public FighterAssetLoader createScene() {
        if (this.asset == null) {
            throw new IllegalStateException("Asset must be loaded before creating a scene.");
        }

        this.scene = new Scene(this.asset.scene);
        return this;
    }

    public Scene build() {
        if (this.scene == null) {
            this.createScene();
        }

        return this.scene;
    }

    @Override
    public void dispose() {
        if (this.asset != null) {
            this.asset.dispose();
        }

        if(this.loader != null) {
            loader.dispose();
        }
    }
}
