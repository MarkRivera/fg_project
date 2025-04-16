package io.github.fg_project.combat;


// A fighter is made out of health, mp, hitboxes, hurtboxes, state, 3d meshes, collision boxes, push boxes, throw boxes
// position, velocity, acceleration, audio files, materials and shaders

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import io.github.fg_project.combat.commands.InputComponent;
import io.github.fg_project.combat.state.BaseState;
import io.github.fg_project.combat.state.StateManagerComponent;
import io.github.fg_project.components.HealthComponent;
import io.github.fg_project.components.ManaComponent;
import io.github.fg_project.components.PhysicsComponent;
import io.github.fg_project.components.RenderingComponent;
import io.github.fg_project.engine.math.FixedPoint;


public abstract class Fighter {
    public RenderingComponent renderingComponent;
    public PhysicsComponent physicsComponent;
    public HealthComponent healthComponent;
    public ManaComponent manaComponent;
    public InputComponent inputComponent;
    public StateManagerComponent stateManagerComponent;
    // Collision
    // Audio
    public boolean onGround;

    public Fighter(PhysicsComponent physicsComponent,
                   HealthComponent healthComponent,
                   ManaComponent manaComponent,
                   RenderingComponent renderingComponent,
                   InputComponent inputComponent,
                   StateManagerComponent stateManagerComponent) {
        this.physicsComponent = physicsComponent;
        this.healthComponent = healthComponent;
        this.manaComponent = manaComponent;
        this.renderingComponent = renderingComponent;
        this.inputComponent = inputComponent;
        this.stateManagerComponent = stateManagerComponent;

        this.onGround = true;
    }

    public abstract void start();
    public abstract void update(FixedPoint deltaTime);
    public abstract void render();
    public abstract void updateCollisionBounds();
    public abstract void changeState();
    public void setupInstances(Object obj) {
        try {
            Class<?> clazz = ClassReflection.forName(obj.getClass().getName());
            Field[] fields = ClassReflection.getDeclaredFields(clazz);
            for (Field field : fields) {

                if (BaseState.class.isAssignableFrom(field.getType())) {
                    BaseState state = (BaseState) field.get(obj);
                    if (field.getName().equals("currentState")) {
                        continue;
                    }

                    System.out.println(field.getName() + ": " + state);
                    if (state != null) {
                        state.setCore(this);
                    }
                }
            }

        } catch (ReflectionException e) {
            Gdx.app.log("REFLECTION", "Could not get class reference for State Manager");
            throw new RuntimeException(e);
        }
    }
}
