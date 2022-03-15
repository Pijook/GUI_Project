package com.company.container;

import com.company.container.containerTypes.Heavy;

public class ExplodingContainer extends Container implements Heavy {

    private double explosionRadius;
    private String specialProtection;

    public ExplodingContainer(double mass, String specialProtection, Double explosionRadius) {
        super(mass);
        this.specialProtection = specialProtection;
        this.explosionRadius = explosionRadius;
    }

    public Double getExplosionRadius() {
        return explosionRadius;
    }

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
                "containerID: " + getContainerID() + "\n" +
                "mass: " + getMass() + "\n" +
                "explosionRadius: " + getExplosionRadius() + "\n" +
                "specialProtection: " + getSpecialProtection() + "\n" +
                "onShip: " + getOnShip();
    }

}
