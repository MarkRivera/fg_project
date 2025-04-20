package io.github.fg_project.input.actions;

public enum Direction {
    LEFT,
    RIGHT;

    public Direction opposite() {
        return this == LEFT ? RIGHT : LEFT;
    }

    public static Direction of(boolean facingRight) {
        return facingRight ? RIGHT : LEFT;
    }
}
