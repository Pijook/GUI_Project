package com.company.train;

import com.company.warehouse.StoredContainer;

public class IrresponsibleSenderWithDangerousGoods extends Exception {

    private final StoredContainer storedContainer;

    public IrresponsibleSenderWithDangerousGoods(StoredContainer storedContainer){
        this.storedContainer = storedContainer;
    }

    @Override
    public void printStackTrace() {
        System.out.println(storedContainer.toString());
        super.printStackTrace();
    }
}
