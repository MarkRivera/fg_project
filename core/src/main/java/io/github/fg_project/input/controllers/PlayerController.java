package io.github.fg_project.input.controllers;

import com.badlogic.gdx.Gdx;
import io.github.fg_project.combat.Fighter;
import io.github.fg_project.combat.commands.Command;
import io.github.fg_project.combat.commands.concrete.NullCommand;
import io.github.fg_project.combat.commands.concrete.attacks.*;
import io.github.fg_project.combat.commands.concrete.movement.*;
import io.github.fg_project.input.InputMapper;
import io.github.fg_project.input.actions.Direction;
import io.github.fg_project.input.actions.GameAction;

import java.util.EnumMap;
import java.util.EnumSet;

public class PlayerController {
    private final InputMapper inputMapper;
    private final EnumMap<GameAction, Command> actionCommandMap = new EnumMap<>(GameAction.class);
    private final Command nullCommand = new NullCommand();
    private final Fighter fighter;
    private Direction facingDirection = Direction.RIGHT;

    public PlayerController(InputMapper inputMapper, Fighter fighter) {
        this.inputMapper = inputMapper;
        this.fighter = fighter;

        actionCommandMap.put(GameAction.UP, new JumpCommand());
        actionCommandMap.put(GameAction.DOWN, new CrouchCommand());
        actionCommandMap.put(GameAction.FORWARD, new WalkForwardCommand());
        actionCommandMap.put(GameAction.BACK, new WalkBackCommand());
        actionCommandMap.put(GameAction.LIGHTATK, new LightAttackCommand());
        actionCommandMap.put(GameAction.MEDIUMATK, new MediumAttackCommand());
        actionCommandMap.put(GameAction.HEAVYATK, new HeavyAttackCommand());
        actionCommandMap.put(GameAction.SPECIALATK, new SpecialAttackCommand());
        actionCommandMap.put(GameAction.ASSISTONE, new AssistOneCommand());
        actionCommandMap.put(GameAction.ASSISTTWO, new AssistTwoCommand());
    }

    public Command getCommand() {
        EnumSet<GameAction> rawActions = inputMapper.pollInput();
        EnumSet<GameAction> resolvedActions = resolveFacingDirection(rawActions);

        if (isTwoAttacksPressed(resolvedActions)) {
            return new DashCommand();
        }

        if (resolvedActions.contains(GameAction.UP)) {
            if (resolvedActions.contains(GameAction.FORWARD)) return new DiagonalJumpCommand(fighter.getFacingDirection());
            if (resolvedActions.contains(GameAction.BACK)) return new DiagonalJumpCommand(fighter.getFacingDirection());
            return new JumpCommand();
        }

        for (GameAction action : resolvedActions) {
            Gdx.app.log("PLAYER CONTROLLER", "CONTROLLER PRESSED: " + action.name());

            Command command = actionCommandMap.get(action);
            if (command != null) {
                return command;
            }
        }

        return nullCommand;
    }

    private EnumSet<GameAction> resolveFacingDirection(EnumSet<GameAction> rawActions) {
        EnumSet<GameAction> resolved = EnumSet.noneOf(GameAction.class);
        Direction facing = fighter.getFacingDirection();

        // TODO: DOUBLE CHECK THIS LOGIC
        for (GameAction action : rawActions) {
            switch (action) {
                case LEFT:
                    resolved.add(facing == Direction.LEFT ? GameAction.FORWARD : GameAction.BACK);
                    break;
                case RIGHT:
                    resolved.add(facing == Direction.RIGHT ? GameAction.FORWARD : GameAction.BACK);
                    break;
                default:
                    resolved.add(action);
                    break;
            }
        }

        return resolved;
    }

    private boolean isTwoAttacksPressed(EnumSet<GameAction> actions) {
        boolean lightAndMedium = actions.contains(GameAction.LIGHTATK) && actions.contains(GameAction.MEDIUMATK);
        boolean lightAndHeavy = actions.contains(GameAction.LIGHTATK) && actions.contains(GameAction.HEAVYATK);
        boolean mediumAndHeavy = actions.contains(GameAction.MEDIUMATK) && actions.contains(GameAction.HEAVYATK);

        return lightAndMedium || lightAndHeavy || mediumAndHeavy;
    }

    public void bindAction(GameAction action, Command command) {
        actionCommandMap.put(action, command);
    }
}
