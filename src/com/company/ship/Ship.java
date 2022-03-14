package com.company.ship;

import com.company.container.Container;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    private String shipName;
    private String port;
    private String from;
    private String to;

    private Integer maxContainers;
    private Double maxContainersMass;
    private Integer maxDangerousContainers;
    private Integer maxHeavyContainers;
    private Integer maxContainersWithElectricity;

    private List<Container> containers;

    public Ship(String shipName, String port, String from, String to, Integer maxContainers, Double maxContainersMass, Integer maxDangerousContainers, Integer maxHeavyContainers, Integer maxContainersWithElectricity) {
        this.shipName = shipName;
        this.port = port;
        this.from = from;
        this.to = to;
        this.maxContainers = maxContainers;
        this.maxContainersMass = maxContainersMass;
        this.maxDangerousContainers = maxDangerousContainers;
        this.maxHeavyContainers = maxHeavyContainers;
        this.maxContainersWithElectricity = maxContainersWithElectricity;

        this.containers = new ArrayList<>();
    }

    public void loadContainer(Container container, boolean force){
        if(force){
            containers.add(container);
        }
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    @Override
    public String toString() {
        return "Ship " +
                "Name: " + shipName + '\'' +
                "Port: " + port + '\'' +
                "From: " + from + '\'' +
                "To: " + to + '\'' +
                "Max Containers: " + maxContainers +
                "Max containers mass:" + maxContainersMass +
                "Max dangerous containers:" + maxDangerousContainers +
                "Max heavy containers:" + maxHeavyContainers +
                "Max containers with electricity:" + maxContainersWithElectricity;
    }
}
