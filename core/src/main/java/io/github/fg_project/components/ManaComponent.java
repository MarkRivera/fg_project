package io.github.fg_project.components;

import io.github.fg_project.engine.math.FixedPoint;

public class ManaComponent {
    public FixedPoint currentMana;
    public FixedPoint maxMana;

    public ManaComponent(int currentMana, int maxMana) {
        this.currentMana = FixedPoint.fromInt(currentMana);
        this.maxMana = FixedPoint.fromInt(maxMana);
    }

    public FixedPoint getCurrentMana() {
        return currentMana;
    }

    public FixedPoint getMaxMana() {
        return maxMana;
    }

    public void consumeMana(FixedPoint cost) {
        this.currentMana = this.currentMana.subtract(cost);

        if (this.currentMana.toInt() <= 0) {
            this.currentMana = FixedPoint.fromInt(0);
        }
    }

    public void addMana(FixedPoint mp) {
        this.currentMana = this.currentMana.add(mp);

        if(this.currentMana.toInt() > this.maxMana.toInt()) {
            this.currentMana = this.maxMana;
        }
    }
}
