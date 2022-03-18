package com.company.container;

import com.company.container.containerTypes.Heavy;

public class HeavyContainer extends Container implements Heavy {

    private String specialProtection;

    public HeavyContainer(double mass, String specialProtection) {
        super(mass);
        this.specialProtection = specialProtection;
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
        return "type: Heavy\n" +
                "containerID: " + getContainerID() + "\n" +
                "mass: " + getMass() + "\n" +
                "specialProtection: " + getSpecialProtection() + "\n" +
                "senderID: " + getSenderID() + "\n" +
                "onShip: " + getOnShip();
    }

}
