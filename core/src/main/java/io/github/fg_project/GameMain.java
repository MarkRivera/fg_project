package io.github.fg_project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.FighterFactory;
import io.github.fg_project.combat.commands.InputComponent;
import io.github.fg_project.combat.kafu.Kafu;
import io.github.fg_project.combat.kafu.state.KafuStateMachine;
import io.github.fg_project.components.HealthComponent;
import io.github.fg_project.components.ManaComponent;
import io.github.fg_project.components.PhysicsComponent;
import io.github.fg_project.components.RenderingComponent;
import io.github.fg_project.engine.math.FixedPoint;
import io.github.fg_project.engine.math.Vec3fp;
import io.github.fg_project.render.DebugCamera;
import io.github.fg_project.render.lights.DirectionalLightExBuilder;
import io.github.fg_project.render.shaders.providers.AnimeShaderProvider;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;

public class GameMain extends ApplicationAdapter
{
    private Fighter player1;

    private SceneManager sceneManager;
    private DebugCamera cameraBuilder;
    private PerspectiveCamera camera;
    private Cubemap diffuseCubemap;
    private Cubemap environmentCubemap;
    private Cubemap specularCubemap;
    private Texture brdfLUT;
    private float time;
    private SceneSkybox skybox;

    private DirectionalLightExBuilder lightBuilder;
    private DirectionalLightEx light;

    @Override
    public void create() {
        cameraBuilder = new DebugCamera();
        cameraBuilder
            .setFOV(75f)
            .setPosition(0, 1.5f,3f)
            .pointCameraAt(0f, 1.5f, 0f)
            .setNear(0.1f)
            .setFar(100f);

        camera = cameraBuilder.build();

        lightBuilder = new DirectionalLightExBuilder();
        lightBuilder
            .setDirection(1, -3, 1)
            .normalize()
            .setColor(Color.WHITE);

        light = lightBuilder.build();

        sceneManager = new SceneManager(75);

        sceneManager.setCamera(camera);
        sceneManager.environment.add(light);

        // setup quick IBL (image based lighting)
        IBLBuilder iblBuilder = IBLBuilder.createOutdoor(light);
        environmentCubemap = iblBuilder.buildEnvMap(1024);
        diffuseCubemap = iblBuilder.buildIrradianceMap(256);
        specularCubemap = iblBuilder.buildRadianceMap(10);
        iblBuilder.dispose();

        // This texture is provided by the library, no need to have it in your assets.
        brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));

        sceneManager.setAmbientLight(1f);
        sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLUT));
        sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(specularCubemap));
        sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap));

        // setup skybox
        skybox = new SceneSkybox(environmentCubemap);
        sceneManager.setSkyBox(skybox);

        Vec3fp player1Pos = new Vec3fp(
            FixedPoint.fromInt(0),
            FixedPoint.fromInt(0),
            FixedPoint.fromInt(0));

        player1 = FighterFactory.createKafu(player1Pos);
        player1.start();

        sceneManager.addScene(player1.renderingComponent.fighterScene);
    }

    @Override
    public void resize(int width, int height) {
        sceneManager.updateViewport(width, height);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;

        // animate camera
        camera.update();

        // Update player 1
        FixedPoint fixedDelta = FixedPoint.fromFloat(deltaTime);
        player1.update();

        // render
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        sceneManager.update(deltaTime);
        sceneManager.setShaderProvider(new AnimeShaderProvider());
        sceneManager.render();
    }

    public void processInput() {}

    public void update() {}

    public void renderFrame() {}

    @Override
    public void dispose() {
        sceneManager.dispose();
        player1.renderingComponent.loader.dispose();

        environmentCubemap.dispose();
        diffuseCubemap.dispose();
        specularCubemap.dispose();
        brdfLUT.dispose();
        skybox.dispose();
    }
}
