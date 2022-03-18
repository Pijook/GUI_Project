package com.company.warehouse;

import com.company.container.Container;

import java.time.LocalDate;

public class StoredContainer {

    private final LocalDate storeDate;
    private final Container container;

    public StoredContainer(LocalDate storeDate, Container container) {
        this.storeDate = storeDate;
        this.container = container;
    }

    public LocalDate getStoreDate() {
        return storeDate;
    }

    public Container getContainer() {
        return container;
    }

    @Override
    public String toString() {
        return container.toString() + "\n" +
                "storeDate: " + storeDate.toString();
    }
}
