package com.company.container;

public class HazardousContainer extends HeavyContainer implements Hazardous {

    private Double radiationLevel;

    public HazardousContainer(Double mass, String specialProtection, Double radiationLevel) {
        super(mass, specialProtection);
        this.radiationLevel = radiationLevel;
    }

    public Double getRadiationLevel() {
        return radiationLevel;
    }

    public void setRadiationLevel(Double radiationLevel) {
        this.radiationLevel = radiationLevel;
    }

    @Override
    public String toString() {
        return "Type: Hazardous, Radiation level: " + radiationLevel + ", " + super.toString();
    }
}
