package io.github.fg_project.input.bindings;

public record ControllerButtonBinding (int controllerIndex, int buttonCode, PressType pressType) implements InputBinding {
}
