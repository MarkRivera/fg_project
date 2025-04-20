package io.github.fg_project.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import io.github.fg_project.input.actions.GameAction;
import io.github.fg_project.input.bindings.*;

import java.util.EnumSet;

public class InputMapper {
    private final ObjectMap<InputBinding, GameAction> bindings = new ObjectMap<>();
    // Use this to store the previous state of the bindings
    private final ObjectMap<ControllerButtonBinding, Boolean> previousButtonStates = new ObjectMap<>();

    private final ObjectMap<ControllerAxisBinding, Boolean> previousAxisStates = new ObjectMap<>();

    public void bind(InputBinding binding, GameAction action) {
        bindings.put(binding, action);
    }

    public void unbind(InputBinding binding) {
        bindings.remove(binding);
    }

    public EnumSet<GameAction> pollInput() {
        EnumSet<GameAction> actions = EnumSet.noneOf(GameAction.class);

        for (ObjectMap.Entry<InputBinding, GameAction> entry : bindings.entries()) {
            if (isPressed(entry.key)) {
                actions.add(entry.value);
            }
        }

        return actions;
    }

    private boolean isPressed(InputBinding binding) {

        if (binding instanceof KeyboardBinding kb) {
            return kb.pressType() == PressType.JUST_PRESSED
                ? Gdx.input.isKeyJustPressed(kb.keycode())
                : Gdx.input.isKeyPressed(kb.keycode());

        }

        else if (binding instanceof ControllerButtonBinding cb) {
            Array<Controller> controllers = Controllers.getControllers();
            if (controllers.size == 0) return false;

            Controller controller = controllers.get(cb.controllerIndex());
            if (controller == null) return false;

            boolean current = controller.getButton(cb.buttonCode());
            boolean previous = previousButtonStates.get(cb, false);

            return cb.pressType() == PressType.JUST_PRESSED
                ? current && !previous
                : current;

        }

        else if (binding instanceof ControllerAxisBinding ca) {
            Array<Controller> controllers = Controllers.getControllers();
            if (controllers.size == 0) return false;

            Controller controller = controllers.get(ca.controllerIndex());
            if (controller == null) return false;

            float value = controller.getAxis(ca.axisCode());
            boolean current = (ca.direction() > 0)
                ? value > 0.5f
                : value < -0.5f;
            boolean previous = previousAxisStates.get(ca, false);
            return ca.pressType() == PressType.JUST_PRESSED
                ? current && !previous
                : current;
        }

        return false;
    }

    public void updatePreviousStates() {
        for (ObjectMap.Entry<InputBinding, GameAction> entry : bindings.entries()) {
            InputBinding binding = entry.key;

            if (binding instanceof ControllerButtonBinding cb) {
                Array<Controller> controllers = Controllers.getControllers();
                if (controllers.size == 0) return;

                Controller controller = controllers.get(cb.controllerIndex());
                if (controller != null) {
                    previousButtonStates.put(cb, controller.getButton(cb.buttonCode()));
                }
            }

            else if (binding instanceof ControllerAxisBinding ca) {
                Controller c = Controllers.getControllers().get(ca.controllerIndex());
                if (c != null) {
                    float v = c.getAxis(ca.axisCode());
                    boolean active = ca.direction()>0 ? v>0.5f : v< -0.5f;
                    previousAxisStates.put(ca, active);
                }
            }
        }
    }

}
