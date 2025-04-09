package io.github.fg_project.combat;


// A fighter is made out of health, mp, hitboxes, hurtboxes, state, 3d meshes, collision boxes, push boxes, throw boxes
// position, velocity, acceleration, audio files, materials and shaders

import io.github.fg_project.engine.math.FixedPoint;
import io.github.fg_project.engine.math.Vec3fp;

public class Fighter {
    // Physics
    public Vec3fp position;
    public Vec3fp velocity;
    public Vec3fp acceleration;

    // Combat State and Health
    public FixedPoint health;
    public FixedPoint mp;
    // FighterState
    public boolean onGround;
    // Collision
    // Visuals
    // Audio

    public Fighter(Vec3fp initialPosition) {
        this.position = initialPosition;
        this.velocity = new Vec3fp(FixedPoint.fromInt(0), FixedPoint.fromInt(0), FixedPoint.fromInt(0));
        this.acceleration = new Vec3fp(FixedPoint.fromInt(0), FixedPoint.fromInt(0), FixedPoint.fromInt(0));

        this.health = FixedPoint.fromInt(1000);
        this.mp = FixedPoint.fromInt(25);
        this.onGround = true;
    }

    public void update(FixedPoint deltaTime) {
        // Current State Update

        // Physics Update

        // Update Collision Bounds
    }

    public void updateCollisionBounds() {}
    public void changeState(){}
}
