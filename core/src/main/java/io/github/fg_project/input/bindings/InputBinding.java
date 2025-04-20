package io.github.fg_project.input.bindings;

public sealed interface InputBinding permits KeyboardBinding, ControllerAxisBinding, ControllerButtonBinding {
    PressType pressType();
}
