package io.github.fg_project.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import net.mgsx.gltf.loaders.glb.GLBAssetLoader;
import net.mgsx.gltf.loaders.gltf.GLTFAssetLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class DebugScreen extends ScreenAdapter {
    public Game game;
    private PerspectiveCamera camera;
    private CameraInputController cameraController; // For debugging
    private Environment environment;

    private ModelBatch modelBatch;
    private ModelInstance modelInstance;
    private AnimationController controller;

    public DebugScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Create a perspective camera with 67° FOV
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Position the camera (adjust values based on your scene)
        camera.position.set(0, 5, 5); // x, y, z
        camera.lookAt(0, 0, 0); // Look at the origin
        camera.near = 0.1f; // Near clipping plane
        camera.far = 100f;  // Far clipping plane
        camera.update();

        // Optional: Add camera controls for debugging (WASD + mouse)
        cameraController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(cameraController);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        AssetManager assetManager = new AssetManager();
        assetManager.setLoader(SceneAsset.class, ".gltf", new GLTFAssetLoader());
        assetManager.setLoader(SceneAsset.class, ".glb", new GLBAssetLoader());

        assetManager.load("models/fighters/tifa/source/Tifa 4_1.glb", SceneAsset.class);
        SceneAsset sceneAsset = assetManager.get("myModel.gltf", SceneAsset.class);


        // Initialize animation controller
        controller = new AnimationController(modelInstance);
        controller.setAnimation("Idle", -1); // Loop idle animation
    }

    @Override
    public void resize(int width, int height) {
        // Update camera viewport on window resize
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        ModelBatch modelBatch = new ModelBatch();
        modelBatch.begin(camera);
//        modelBatch.render(yourModelInstance, environment);
        modelBatch.end();
    }

    @Override
    public void hide() {
        super.hide();

        Gdx.input.setInputProcessor(null);
    }
}
