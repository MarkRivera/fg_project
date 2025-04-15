package io.github.fg_project.components;

import io.github.fg_project.engine.math.FixedPoint;

public class HealthComponent {
    public FixedPoint currentHealth;
    public FixedPoint maxHealth;

    public HealthComponent(int currentHealth, int maxHealth) {
        this.currentHealth = FixedPoint.fromInt(currentHealth);
        this.maxHealth = FixedPoint.fromInt(maxHealth);
    }

    public FixedPoint getCurrentHealth() {
        return currentHealth;
    }

    public FixedPoint getMaxHealth() {
        return maxHealth;
    }

    public void takeDamage(FixedPoint damage) {
        this.currentHealth = this.currentHealth.subtract(damage);

        if (this.currentHealth.toInt() <= 0) {
            this.currentHealth = FixedPoint.fromInt(0);
            System.out.println("FIghter has died");
        }
    }

    public void heal(FixedPoint healthGained) {
        this.currentHealth = this.currentHealth.add(healthGained);

        if(this.currentHealth.toInt() > this.maxHealth.toInt()) {
            this.currentHealth = this.maxHealth;
        }
    }
}
