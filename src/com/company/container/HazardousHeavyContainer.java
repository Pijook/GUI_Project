package com.company.container;

public class HazardousHeavyContainer extends HeavyContainer implements Hazardous {

    private Double radiationLevel;

    public HazardousHeavyContainer(Double mass, String specialProtection, Double radiationLevel) {
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
        return "type: HeavyHazardous\n" +
                "containerID: " + getContainerID() + "\n" +
                "mass: " + getMass() + "\n" +
                "specialProtection: " + getSpecialProtection() + "\n" +
                "radiationLevel: " + getRadiationLevel() + "\n" +
                "onShip: " + getOnShip();
    }
}
