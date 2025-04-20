package io.github.fg_project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.Controllers;
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
import io.github.fg_project.input.InputMapper;
import io.github.fg_project.input.actions.GameAction;
import io.github.fg_project.input.bindings.ControllerAxisBinding;
import io.github.fg_project.input.bindings.ControllerButtonBinding;
import io.github.fg_project.input.bindings.KeyboardBinding;
import io.github.fg_project.input.bindings.PressType;
import io.github.fg_project.render.DebugCamera;
import io.github.fg_project.render.lights.DirectionalLightExBuilder;
import io.github.fg_project.render.shaders.providers.AnimeShaderProvider;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;

import java.util.EnumSet;

public class GameMain extends ApplicationAdapter
{
    private InputMapper inputMapper;
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
        Controllers.addListener(new ControllerAdapter() {
            @Override
            public boolean buttonDown(Controller controller, int buttonCode) {
                System.out.println("Button down: " + buttonCode);
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                if (Math.abs(value) > 0.2f) { // filter deadzone
                    System.out.println("Axis moved: " + axisCode + " value: " + value);
                }
                return false;
            }
        });

        // TODO: Implement KEY BINDING MENU
        inputMapper = new InputMapper();
        inputMapper.bind(new KeyboardBinding(Input.Keys.SPACE, PressType.JUST_PRESSED), GameAction.UP);
        inputMapper.bind(new KeyboardBinding(Input.Keys.A, PressType.PRESSED), GameAction.LEFT);
        inputMapper.bind(new KeyboardBinding(Input.Keys.D, PressType.PRESSED), GameAction.RIGHT);
        inputMapper.bind(new KeyboardBinding(Input.Keys.S, PressType.PRESSED), GameAction.DOWN);

        Controller controller = Controllers.getCurrent();
        if (controller != null) {
            ControllerMapping mapping = controller.getMapping();
            inputMapper.bind(new ControllerButtonBinding(controller.getPlayerIndex(), mapping.buttonDpadUp, PressType.JUST_PRESSED), GameAction.UP);
            inputMapper.bind(new ControllerButtonBinding(controller.getPlayerIndex(), mapping.buttonDpadDown, PressType.PRESSED), GameAction.DOWN);
            inputMapper.bind(new ControllerButtonBinding(controller.getPlayerIndex(), mapping.buttonDpadLeft, PressType.PRESSED), GameAction.LEFT);
            inputMapper.bind(new ControllerButtonBinding(controller.getPlayerIndex(), mapping.buttonDpadRight, PressType.PRESSED), GameAction.RIGHT);

            inputMapper.bind(new ControllerButtonBinding(controller.getPlayerIndex(), mapping.buttonX, PressType.JUST_PRESSED), GameAction.LIGHTATK);
            inputMapper.bind(new ControllerButtonBinding(controller.getPlayerIndex(), mapping.buttonY, PressType.JUST_PRESSED), GameAction.MEDIUMATK);
            inputMapper.bind(new ControllerButtonBinding(controller.getPlayerIndex(), mapping.buttonR1, PressType.JUST_PRESSED), GameAction.HEAVYATK);
            inputMapper.bind(new ControllerButtonBinding(controller.getPlayerIndex(), mapping.buttonA, PressType.JUST_PRESSED), GameAction.SPECIALATK);

            inputMapper.bind(new ControllerButtonBinding(controller.getPlayerIndex(), mapping.buttonB, PressType.JUST_PRESSED), GameAction.ASSISTONE);
            inputMapper.bind(new ControllerAxisBinding(controller.getPlayerIndex(), 5, 1.0f, PressType.JUST_PRESSED), GameAction.ASSISTTWO);

            inputMapper.bind(new ControllerButtonBinding(controller.getPlayerIndex(), mapping.buttonStart, PressType.JUST_PRESSED), GameAction.PAUSE);
        }



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
        EnumSet<GameAction> currentActions = inputMapper.pollInput();

        for (GameAction action : currentActions) {
            switch (action) {
                case UP         -> Gdx.app.log("INPUT MAPPER", "CONTROLLER PRESSED JUMP");
                case DOWN       ->  Gdx.app.log("INPUT MAPPER", "CONTROLLER PRESSED CROUCH");
                case LEFT       -> Gdx.app.log("INPUT MAPPER", "CONTROLLER PRESSED LEFT");
                case RIGHT      ->  Gdx.app.log("INPUT MAPPER", "CONTROLLER PRESSED RIGHT");

                case PAUSE      -> Gdx.app.log("INPUT MAPPER", "CONTROLLER PRESSED PAUSE");

                case LIGHTATK   -> Gdx.app.log("INPUT MAPPER", "CONTROLLER PRESSED LIGHT ATTACK");
                case MEDIUMATK  -> Gdx.app.log("INPUT MAPPER", "CONTROLLER PRESSED MEDIUM ATTACK");
                case HEAVYATK   -> Gdx.app.log("INPUT MAPPER", "CONTROLLER PRESSED HEAVY ATTACK");
                case SPECIALATK -> Gdx.app.log("INPUT MAPPER", "CONTROLLER PRESSED SPECIAL ATTACK");
                case ASSISTONE  -> Gdx.app.log("INPUT MAPPER", "CONTROLLER PRESSED ASSIST ONE");
                case ASSISTTWO  -> Gdx.app.log("INPUT MAPPER", "CONTROLLER PRESSED ASSIST TWO");
            }
        }

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

        inputMapper.updatePreviousStates();
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
