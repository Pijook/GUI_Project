package com.company.sender;

import com.company.warehouse.StoredContainer;

import java.time.LocalDate;

public class Warning {

    private LocalDate warnDate;
    private StoredContainer storedContainer;

    public Warning(LocalDate warnDate, StoredContainer storedContainer) {
        this.warnDate = warnDate;
        this.storedContainer = storedContainer;
    }

    public LocalDate getWarnDate() {
        return warnDate;
    }

    public void setWarnDate(LocalDate warnDate) {
        this.warnDate = warnDate;
    }

    public StoredContainer getStoredContainer() {
        return storedContainer;
    }

    public void setStoredContainer(StoredContainer storedContainer) {
        this.storedContainer = storedContainer;
    }

    @Override
    public String toString() {
        return storedContainer.toString() + "\n" +
                "warnDate: " + warnDate.toString();
    }
}
