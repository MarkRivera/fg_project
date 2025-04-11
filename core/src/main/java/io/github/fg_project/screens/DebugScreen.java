package io.github.fg_project.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import net.mgsx.gltf.loaders.glb.GLBAssetLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;

public class DebugScreen extends ScreenAdapter {
    private SceneManager sceneManager;
    private PerspectiveCamera camera;
    private CameraInputController cameraController;
    private ModelInstance axesInstance;
    private ModelBatch modelBatch;

    public DebugScreen() {
        setupCamera();
        setupSceneManager();
        createAxes();
        loadModel();
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST); // Enable depth test globally
    }

    private void setupCamera() {
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0, 2, 5); // Back up camera
        camera.lookAt(0, 1, 0);       // Look at center
        camera.near = 0.1f;           // Safer near plane
        camera.far = 100f;
        camera.update();

        cameraController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(new InputMultiplexer(cameraController));
    }

    private void setupSceneManager() {
        sceneManager = new SceneManager();

        // Enhanced lighting setup
        sceneManager.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 1, 1));
        sceneManager.environment.add(new DirectionalLight()
            .set(1, 1, 1, new Vector3(-1, -3, -2).nor())); // Better light angle
    }

    private void createAxes() {
        ModelBuilder modelBuilder = new ModelBuilder();
        Model axesModel = modelBuilder.createXYZCoordinates(10, new Material(),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked);
        axesInstance = new ModelInstance(axesModel);
        modelBatch = new ModelBatch();
    }

    private void loadModel() {
        AssetManager assetManager = new AssetManager();
        assetManager.setLoader(SceneAsset.class, ".glb", new GLBAssetLoader());

        // Load model with error handling
        String modelPath = "models/fighters/tifa/source/Tifa 4_1.glb"; // Remove spaces
        assetManager.load(modelPath, SceneAsset.class);

        try {
            assetManager.finishLoading();
            SceneAsset sceneAsset = assetManager.get(modelPath);
            Scene scene = new Scene(sceneAsset.scene);

            // Reset transformations
            scene.modelInstance.transform.idt();
            scene.modelInstance.transform.scale(0.5f, 0.5f, 0.5f);

            sceneManager.addScene(scene);
            sceneManager.setCamera(camera);

            // Log model info
            BoundingBox bb = new BoundingBox();
            scene.modelInstance.calculateBoundingBox(bb);
            Gdx.app.log("MODEL", "Loaded successfully. Bounds: " + bb.getDimensions(new Vector3()));

        } catch (Exception e) {
            Gdx.app.error("LOAD", "Failed to load model", e);
        }
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        updateScene(delta);
        renderFrame();
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        // Camera controls
        float speed = 2f * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.add(camera.direction.cpy().scl(speed));
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.sub(camera.direction.cpy().scl(speed));
        cameraController.update();
    }

    private void updateScene(float delta) {
        camera.update();
        sceneManager.update(delta);
    }

    private void renderFrame() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        sceneManager.render();

        // Render axes on top
        modelBatch.begin(camera);
        modelBatch.render(axesInstance);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        sceneManager.dispose();
        modelBatch.dispose();
    }
}
