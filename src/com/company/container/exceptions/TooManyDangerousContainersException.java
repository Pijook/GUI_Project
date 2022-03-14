package com.company.container.exceptions;

public class TooManyDangerousContainersException extends Exception{

    @Override
    public void printStackTrace() {
        System.out.println("Too many dangerous containers on ship!");
        super.printStackTrace();
    }
}
