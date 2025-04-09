package io.github.fg_project.engine.math.test;

import io.github.fg_project.engine.math.FixedTrig;

public class FixedTrigTest {
    public static void main(String[] args) {
        testFixedTrig();
    }

    public static void testFixedTrig() {
        System.out.println("=== FixedTrig Test ===");

        float[] testAngles = {0f, 30f, 45f, 90f, 180f, 270f, 360f, -90f, 720f, 15.5f};
        float tolerance = 0.001f;

        for (float degrees : testAngles) {
            int fixedAngle = FixedTrig.degreesToAngle(degrees);
            float computedDegrees = FixedTrig.angleToDegrees(fixedAngle);

            int sinFixed = FixedTrig.sin(fixedAngle);
            int cosFixed = FixedTrig.cos(fixedAngle);

            float sinValue = (float) sinFixed / FixedTrig.SCALE;
            float cosValue = (float) cosFixed / FixedTrig.SCALE;

            // Reference values (using normalized degrees)
            float normalizedDegrees = degrees % 360;
            if (normalizedDegrees < 0) normalizedDegrees += 360;
            double rad = Math.toRadians(normalizedDegrees);
            float expectedSin = (float) Math.sin(rad);
            float expectedCos = (float) Math.cos(rad);

            // Check results
            boolean sinPass = Math.abs(sinValue - expectedSin) < tolerance;
            boolean cosPass = Math.abs(cosValue - expectedCos) < tolerance;
            boolean anglePass = Math.abs(computedDegrees - normalizedDegrees) < 0.1f;

            System.out.printf(
                "Angle: %6.1f° → Fixed: %8d → Sin: %8.4f (expected %8.4f) %s | Cos: %8.4f (expected %8.4f) %s%n",
                degrees,
                fixedAngle,
                sinValue, expectedSin, sinPass ? "✓" : "✗",
                cosValue, expectedCos, cosPass ? "✓" : "✗"
            );

            if (!sinPass || !cosPass || !anglePass) {
                System.err.printf("FAILURE @ %.1f°: sinErr=%.4f, cosErr=%.4f, angleErr=%.2f°%n",
                    degrees,
                    sinValue - expectedSin,
                    cosValue - expectedCos,
                    computedDegrees - normalizedDegrees
                );
            }
        }
        System.out.println("=== Test Complete ===");
    }
}
