package io.github.fg_project.input.bindings;

public record ControllerAxisBinding(int controllerIndex, int axisCode, float direction, PressType pressType) implements InputBinding {
}
