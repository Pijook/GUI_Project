package com.company.container;

public class NormalContainer extends Container {

    public NormalContainer(double mass) {
        super(mass);
    }

    @Override
    public String toString() {
        return "type: Normal\n" + super.toString();
    }
}
