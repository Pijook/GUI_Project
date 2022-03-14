package com.company.container;

public class ExplodingContainer extends HeavyContainer {

    private Double explosionRadius;

    public ExplodingContainer(Double mass, String specialProtection, Double explosionRadius) {
        super(mass, specialProtection);
        this.explosionRadius = explosionRadius;
    }

    public Double getExplosionRadius() {
        return explosionRadius;
    }

    public void setExplosionRadius(Double explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    @Override
    public String toString() {
        return "Type: Exploding, Explosion radius: " + explosionRadius + ", " + super.toString();
    }
}
