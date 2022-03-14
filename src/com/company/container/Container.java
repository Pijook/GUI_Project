package com.company.container;

import java.util.UUID;

public class Container {

    private final UUID containerID;
    private Double mass;

    public Container(Double mass){
        this.containerID = UUID.randomUUID();
        this.mass = mass;
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
}
