package com.company.menu;

public class Option {

    private String optionName;
    private Runnable runnable;
    private boolean returnAfterAction;

    public Option(String optionName, Runnable runnable, boolean returnAfterAction) {
        this.optionName = optionName;
        this.runnable = runnable;
        this.returnAfterAction = returnAfterAction;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public boolean isReturnAfterAction() {
        return returnAfterAction;
    }

    public void setReturnAfterAction(boolean returnAfterAction) {
        this.returnAfterAction = returnAfterAction;
    }
}
