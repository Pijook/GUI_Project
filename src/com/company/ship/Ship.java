package com.company.ship;

import com.company.container.Container;

import java.util.List;

public class Ship {

    private Integer maxContainers;
    private Double maxContainersMass;
    private Integer maxDangerousContainers;
    private Integer maxHeavyContainers;
    private Integer maxContainersWithElectricity;

    private List<Container> dangerousContainers;
    private List<Container> heavyContainers;
    private List<Container> containersWithElectricity;

    public Ship(Integer maxContainers, Double maxContainersMass, Integer maxDangerousContainers, Integer maxHeavyContainers, Integer maxContainersWithElectricity) {
        this.maxContainers = maxContainers;
        this.maxContainersMass = maxContainersMass;
        this.maxDangerousContainers = maxDangerousContainers;
        this.maxHeavyContainers = maxHeavyContainers;
        this.maxContainersWithElectricity = maxContainersWithElectricity;
    }

    public Integer getMaxContainers() {
        return maxContainers;
    }

    public void setMaxContainers(Integer maxContainers) {
        this.maxContainers = maxContainers;
    }

    public Double getMaxContainersMass() {
        return maxContainersMass;
    }

    public void setMaxContainersMass(Double maxContainersMass) {
        this.maxContainersMass = maxContainersMass;
    }

    public Integer getMaxDangerousContainers() {
        return maxDangerousContainers;
    }

    public void setMaxDangerousContainers(Integer maxDangerousContainers) {
        this.maxDangerousContainers = maxDangerousContainers;
    }

    public Integer getMaxHeavyContainers() {
        return maxHeavyContainers;
    }

    public void setMaxHeavyContainers(Integer maxHeavyContainers) {
        this.maxHeavyContainers = maxHeavyContainers;
    }

    public Integer getMaxContainersWithElectricity() {
        return maxContainersWithElectricity;
    }

    public void setMaxContainersWithElectricity(Integer maxContainersWithElectricity) {
        this.maxContainersWithElectricity = maxContainersWithElectricity;
    }

    public List<Container> getDangerousContainers() {
        return dangerousContainers;
    }

    public void setDangerousContainers(List<Container> dangerousContainers) {
        this.dangerousContainers = dangerousContainers;
    }

    public List<Container> getHeavyContainers() {
        return heavyContainers;
    }

    public void setHeavyContainers(List<Container> heavyContainers) {
        this.heavyContainers = heavyContainers;
    }

    public List<Container> getContainersWithElectricity() {
        return containersWithElectricity;
    }

    public void setContainersWithElectricity(List<Container> containersWithElectricity) {
        this.containersWithElectricity = containersWithElectricity;
    }
}
