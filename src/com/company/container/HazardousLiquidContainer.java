package com.company.container;

public class HazardousLiquidContainer extends LiquidContainer implements Hazardous {

    private Double radiationLevel;

    public HazardousLiquidContainer(Double mass, double maxCapacity, Double radiationLevel) {
        super(mass, maxCapacity);
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
        return "Type: Hazardous liquid, Radiation level:" + radiationLevel + ", " + super.toString();
    }
}
