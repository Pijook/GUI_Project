package com.company.container;

import com.company.container.containerTypes.Exploding;
import com.company.container.containerTypes.Heavy;

public class ExplodingContainer extends Container implements Heavy, Exploding {

    private double explosionRadius;
    private String specialProtection;

    public ExplodingContainer(double mass, String specialProtection, Double explosionRadius) {
        super(mass);
        this.specialProtection = specialProtection;
        this.explosionRadius = explosionRadius;
    }

    @Override
    public double getExplosionRadius() {
        return explosionRadius;
    }

    @Override
    public void setExplosionRadius(double explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    @Override
    public String getSpecialProtection() {
        return specialProtection;
    }

    @Override
    public void setSpecialProtection(String specialProtection) {
        this.specialProtection = specialProtection;
    }

    @Override
    public String toString() {
        return "type: Exploding\n" +
                "explosionRadius: " + explosionRadius + "\n" +
                "specialProtection: " + specialProtection + '\n' +
                super.toString();
    }
}
