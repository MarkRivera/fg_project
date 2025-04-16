package io.github.fg_project.combat.commands.concrete;

import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.interfaces.Command;

public class NullCommand implements Command {
    @Override
    public void execute(Fighter fighter) {

    }
}
