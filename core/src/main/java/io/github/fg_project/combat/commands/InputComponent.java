package io.github.fg_project.combat.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ObjectMap;
import io.github.fg_project.combat.commands.concrete.*;
import io.github.fg_project.combat.commands.concrete.attacks.LightAttackCommand;
import io.github.fg_project.combat.commands.concrete.movement.CrouchCommand;
import io.github.fg_project.combat.commands.concrete.movement.JumpCommand;
import io.github.fg_project.combat.commands.concrete.movement.WalkBackCommand;
import io.github.fg_project.combat.commands.concrete.movement.WalkForwardCommand;

public class InputComponent {
    private final ObjectMap<Integer, Command> keyCommandMap = new ObjectMap<>();
    private final Command nullCommand;

    public InputComponent() {
        nullCommand = new NullCommand();

        keyCommandMap.put(Input.Keys.SPACE, new JumpCommand());
        keyCommandMap.put(Input.Keys.D, new WalkForwardCommand());
        keyCommandMap.put(Input.Keys.A, new WalkBackCommand());
        keyCommandMap.put(Input.Keys.S, new CrouchCommand());
        keyCommandMap.put(Input.Keys.R, new LightAttackCommand());
    }


    public Command handleInput() {
        for (ObjectMap.Entry<Integer, Command> entry : keyCommandMap.entries()) {
            if (isPressed(entry.key)) {
                return entry.value;
            }
        }

        return nullCommand;
    }

    public void bindKey(int key, Command command) {
        keyCommandMap.put(key, command);
    }

    public void rebindKey(int oldKey, int newKey) {
        Command command = keyCommandMap.remove(oldKey);
        if (command != null) {
            keyCommandMap.put(newKey, command);
        }
    }


    private boolean isPressed(int key) {
        return Gdx.input.isKeyJustPressed(key);
    }
}
