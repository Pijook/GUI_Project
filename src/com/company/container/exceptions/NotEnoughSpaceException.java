package com.company.container.exceptions;

public class NotEnoughSpaceException extends Exception {

    @Override
    public void printStackTrace() {
        System.out.println("Not enough space on ship!");
        super.printStackTrace();
    }
}
