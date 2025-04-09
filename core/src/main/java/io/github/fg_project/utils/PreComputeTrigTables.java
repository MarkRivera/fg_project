package io.github.fg_project.utils;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PreComputeTrigTables {
    private static final int SCALE = 1 << 16;  // 16-bit fixed-point scaling
    private static final int MAX_ANGLE = 65536; // 2^16 angles

    public static void main(String[] args) throws IOException {
        int[] sinTable = new int[MAX_ANGLE];
        int[] cosTable = new int[MAX_ANGLE];

        // Generate tables
        for (int i = 0; i < MAX_ANGLE; i++) {
            double angleRadians = 2 * Math.PI * i / MAX_ANGLE;
            sinTable[i] = (int) Math.round(Math.sin(angleRadians) * SCALE);
            cosTable[i] = (int) Math.round(Math.cos(angleRadians) * SCALE);
        }

        // Write to file
        Path path = Path.of("trig_tables.bin");
        try (DataOutputStream dos = new DataOutputStream(
            Files.newOutputStream(path)))
        {
            for (int value : sinTable) dos.writeInt(value);
            for (int value : cosTable) dos.writeInt(value);
        }

        System.out.println("Tables generated to: " + path.toAbsolutePath());
    }
}
