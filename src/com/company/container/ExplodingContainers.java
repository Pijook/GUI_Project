package com.company.container;

public class ExplodingContainers extends HeavyContainer {

    private Double explosionRadius;

    public ExplodingContainers(Double mass, String specialProtection, Double explosionRadius) {
        super(mass, specialProtection);
        this.explosionRadius = explosionRadius;
    }

    public Double getExplosionRadius() {
        return explosionRadius;
    }

    public void setExplosionRadius(Double explosionRadius) {
        this.explosionRadius = explosionRadius;
    }
}
