package io.github.fg_project.engine.math;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FixedTrig {
    // Fixed-point precision constants (matches your table scaling)
    private static final int SCALE_BITS = 16;
    public static final int SCALE = 1 << SCALE_BITS;
    private static final int MAX_ANGLE = 65536;  // 2^16 entries
    private static final int ANGLE_MASK = 0xFFFF;

    public record SinCos(int sin, int cos) {}

    // Storage for precomputed values
    private static final int[] sinTable = new int[MAX_ANGLE];
    private static final int[] cosTable = new int[MAX_ANGLE];

    static {
        // Load tables when class is initialized
        try {
            loadTables();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load trig tables", e);
        }
    }

    private static void loadTables() throws IOException {
        try(InputStream is = FixedTrig.class.getResourceAsStream("/trig_tables.bin");
            DataInputStream dis = new DataInputStream(is))
        {
            for (int i = 0; i < MAX_ANGLE; i++) {
                sinTable[i] = dis.readInt();
            }

            for (int i = 0; i < MAX_ANGLE; i++) {
                cosTable[i] = dis.readInt();
            }
        }
    }

    public static int degreesToAngle(float degrees) {
        // Normalize degrees to [0, 360) range
        float normalized = degrees % 360;
        if (normalized < 0) normalized += 360;
        return (int)(normalized * MAX_ANGLE / 360f);
    }

    public static float angleToDegrees(int fixedPointAngle) {
        return (fixedPointAngle & 0xFFFF) * (360f / MAX_ANGLE);
    }

    public static int sin(int fixedPointAngle) {
        return sinTable[fixedPointAngle & ANGLE_MASK];
    }

    public static int cos(int fixedPointAngle) {
        return cosTable[fixedPointAngle & ANGLE_MASK];
    }
    public static SinCos sincos(int fixedPointAngle) {
        int index = fixedPointAngle & ANGLE_MASK;
        return new SinCos(sinTable[index], cosTable[index]);
    }
}
