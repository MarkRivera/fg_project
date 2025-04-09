package io.github.fg_project.engine.math;

public final class FixedPoint {
    public static final int FRACTIONAL_BITS = 16;
    public static final int SCALE = 1 << FRACTIONAL_BITS; // 65536

    private final int raw;

    private FixedPoint(int rawValue) {
        this.raw = rawValue;
    }

    public static FixedPoint fromFloat(float floatValue) {
        return new FixedPoint((int)(floatValue * SCALE));
    }

    public static FixedPoint fromInt(int intValue) {
        return new FixedPoint(intValue * SCALE);
    }

    public float toFloat() {
        return (float) this.raw / SCALE;
    }

    public int toInt() {
        return this.raw / SCALE;
    }

    public FixedPoint add(FixedPoint other) {
        return new FixedPoint(this.raw + other.raw);
    }

    public FixedPoint subtract(FixedPoint other) {
        return new FixedPoint(this.raw - other.raw);
    }

    public FixedPoint multiply(FixedPoint other) {
        long temp = (long) this.raw * other.raw;
        return new FixedPoint((int)(temp >> FRACTIONAL_BITS));
    }

    public FixedPoint divide(FixedPoint other) {
        if (other.raw == 0) {
            return new FixedPoint(0);
        }

        long temp = ((long) this.raw << FRACTIONAL_BITS);
        return new FixedPoint((int)(temp / other.raw));
    }

    @Override
    public String toString() {
        return Float.toString(this.toFloat());
    }
}
