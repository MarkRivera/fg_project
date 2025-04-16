package io.github.fg_project.combat.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.fg_project.combat.commands.concrete.*;
import io.github.fg_project.combat.interfaces.Command;

public class InputComponent {
    private final int SPACE_BAR = Input.Keys.SPACE;
    private final int R_KEY = Input.Keys.R;
    private final int A_KEY = Input.Keys.A;
    private final int D_KEY = Input.Keys.D;
    private final int S_KEY = Input.Keys.S;

    private Command space;
    private Command forward;
    private Command back;
    private Command down;
    private Command attack;
    private final Command nullCommand;

    public InputComponent() {
        this.space = new JumpCommand();
        this.forward = new WalkForwardCommand();
        this.back = new WalkBackCommand();
        this.attack = new LightAttackCommand();
        this.down = new CrouchCommand();
        this.nullCommand = new NullCommand();
    }


    public Command handleInput() {
        if (isPressed(SPACE_BAR)) {
            return space;
        } else if (isPressed(R_KEY)) {
            return attack;
        } else if (isPressed(A_KEY)) {
            return back;
        } else if (isPressed(D_KEY)) {
            return forward;
        } else if (isPressed(S_KEY)) {
            return down;
        }

        return null;
    }

    public void setAttack(Command command) {
        this.attack = command;
    }

    public void setSpace(Command command) {
        this.space = command;
    }

    public void setBack(Command command) {
        this.back = command;
    }

    public void setDown(Command command) {
        this.down = command;
    }

    public void setForward(Command command) {
        this.forward = command;
    }

    private boolean isPressed(int key) {
        return Gdx.input.isKeyJustPressed(key);
    }
}
