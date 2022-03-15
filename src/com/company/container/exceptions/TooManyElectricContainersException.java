package com.company.container.exceptions;

public class TooManyElectricContainersException extends Exception {

    @Override
    public void printStackTrace() {
        System.out.println("Too many electric containers on ship!");
        super.printStackTrace();
    }

}
