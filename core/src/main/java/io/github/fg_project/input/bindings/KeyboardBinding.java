package io.github.fg_project.input.bindings;

public record KeyboardBinding(int keycode, PressType pressType) implements InputBinding {
}
