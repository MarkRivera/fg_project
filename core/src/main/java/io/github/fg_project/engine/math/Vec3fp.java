package io.github.fg_project.engine.math;

public class Vec3fp {
    public FixedPoint x, y, z;

    public Vec3fp(FixedPoint x, FixedPoint y, FixedPoint z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3fp add(Vec3fp other) {
        return new Vec3fp(
            this.x.add(other.x),
            this.y.add(other.y),
            this.z.add(other.z)
        );
    }

    public Vec3fp sub(Vec3fp other) {
        return new Vec3fp(
            this.x.subtract(other.x),
            this.y.subtract(other.y),
            this.z.subtract(other.z)
        );
    }

    public Vec3fp mul(FixedPoint scalar) {
        return new Vec3fp(
            this.x.multiply(scalar),
            this.y.multiply(scalar),
            this.z.multiply(scalar)
        );
    }

    public FixedPoint dot(Vec3fp other) {
        return this.x.multiply(other.x)
            .add(this.y.multiply(other.y))
            .add(this.z.multiply(other.z));
    }

    @Override
    public String toString() {
        return "(" + x.toString() + ", " + y.toString() + ", " + z.toString() + ")";
    }
}
