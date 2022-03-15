package com.company.container;

public class HeavyContainer extends Container {

    private String specialProtection;

    public HeavyContainer(Double mass, String specialProtection) {
        super(mass);
        this.specialProtection = specialProtection;
    }

    public String getSpecialProtection() {
        return specialProtection;
    }

    public void setSpecialProtection(String specialProtection) {
        this.specialProtection = specialProtection;
    }

    @Override
    public String toString() {
        return "type: Heavy\n" +
                "containerID: " + getContainerID() + "\n" +
                "mass: " + getMass() + "\n" +
                "specialProtection: " + getSpecialProtection() + "\n" +
                "onShip: " + getOnShip();
    }
}
