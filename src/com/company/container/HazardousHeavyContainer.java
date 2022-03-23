package com.company.container;

import com.company.container.containerTypes.Hazardous;
import com.company.container.containerTypes.Heavy;

public class HazardousHeavyContainer extends Container implements Hazardous, Heavy {

    private double radiationLevel;
    private String specialProtection;

    public HazardousHeavyContainer(double mass, String specialProtection, double radiationLevel) {
        super(mass);
        this.specialProtection = specialProtection;
        this.radiationLevel = radiationLevel;
    }

    @Override
    public double getRadiationLevel() {
        return radiationLevel;
    }

    @Override
    public void setRadiationLevel(double radiationLevel) {
        this.radiationLevel = radiationLevel;
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
        return "type: HeavyHazardous\n" +
                "radiationLevel: " + radiationLevel + "\n" +
                "specialProtection: " + specialProtection + "\n" +
                super.toString();
    }
}
