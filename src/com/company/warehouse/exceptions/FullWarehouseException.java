package com.company.warehouse.exceptions;

public class FullWarehouseException extends Exception {

    @Override
    public void printStackTrace() {
        System.out.println("Warehouse is full");
    }
}
