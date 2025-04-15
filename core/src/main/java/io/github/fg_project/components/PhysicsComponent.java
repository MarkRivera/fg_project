package io.github.fg_project.components;

import io.github.fg_project.engine.math.Vec3fp;

public class PhysicsComponent {
    public Vec3fp position;
    public Vec3fp velocity;
    public Vec3fp acceleration;

    public PhysicsComponent(Vec3fp initialPosition) {
        this.position = initialPosition;
        this.velocity = Vec3fp.zero();
        this.acceleration = Vec3fp.zero();
    }

}
