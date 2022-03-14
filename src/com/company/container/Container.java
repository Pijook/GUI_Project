package com.company.container;

import java.util.UUID;

public class Container {

    private UUID containerID;
    private Double mass;
    private String onShip;

    public Container(Double mass){
        this.containerID = UUID.randomUUID();
        this.mass = mass;
        this.onShip = null;
    }

    public UUID getContainerID() {
        return containerID;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public void setContainerID(UUID containerID) {
        this.containerID = containerID;
    }

    public String getOnShip() {
        return onShip;
    }

    public void setOnShip(String onShip) {
        this.onShip = onShip;
    }

    @Override
    public String toString() {
        return "ID:" + containerID + ", Mass:" + mass;
    }
}
