package com.company.menu;

public class Option {

    private String optionName;
    private Runnable runnable;

    public Option(String optionName, Runnable runnable) {
        this.optionName = optionName;
        this.runnable = runnable;
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
}
