package io.github.fg_project.engine;

import io.github.fg_project.engine.math.FixedPoint;
import io.github.fg_project.engine.math.Vec3fp;

public class Physics {
    private static final FixedPoint GRAVITY = FixedPoint.fromFloat(9.8f);
    private static final Vec3fp GRAVITY_VECTOR = new Vec3fp(
        FixedPoint.fromInt(0),
        GRAVITY.multiply(FixedPoint.fromInt(-1)),
        FixedPoint.fromInt(0)
    );
}
