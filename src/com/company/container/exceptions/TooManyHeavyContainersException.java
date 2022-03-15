package com.company.container.exceptions;

public class TooManyHeavyContainersException extends Exception {

    @Override
    public void printStackTrace() {
        System.out.println("Too many heavy containers on ship!");
        super.printStackTrace();
    }

}
